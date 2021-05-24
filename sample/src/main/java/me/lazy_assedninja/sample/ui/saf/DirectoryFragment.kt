package me.lazy_assedninja.sample.ui.saf

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.lazy_assedninja.library.utils.ExecutorUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.binding.FragmentDataBindingComponent
import me.lazy_assedninja.sample.databinding.FragmentDirectoryBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared
import javax.inject.Inject

private const val ARG_DIRECTORY_URI = "directory_uri"

class DirectoryFragment : BottomSheetDialogFragment() {

    var binding by autoCleared<FragmentDirectoryBinding>()
    private var adapter by autoCleared<DirectoryListAdapter>()

    @Inject
    lateinit var executorUtils: ExecutorUtils

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DirectoryViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var uri: Uri

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Obtaining the login graph from MainActivity and instantiate
        // the @Inject fields with objects from the graph
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_directory,
            container,
            false,
            dataBindingComponent
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        uri = arguments?.getString(ARG_DIRECTORY_URI)?.toUri()
            ?: throw IllegalArgumentException("Must pass URI of directory to open.")

        binding.toolbar.title = context?.let {
            DocumentFile.fromTreeUri(it, uri)?.name
        }

        adapter = DirectoryListAdapter(dataBindingComponent, executorUtils) {
            viewModel.documentClicked(it)
        }
        binding.directoryList.adapter = adapter

        viewModel.documents.observe(viewLifecycleOwner) {
            viewModel.isLoading.set(false)
            adapter.submitList(it)
        }
        viewModel.openDirectory.observe(viewLifecycleOwner) {
            context?.let { context ->
                viewModel.loadDirectory(context, it.uri)
                binding.toolbar.title = DocumentFile.fromTreeUri(context, it.uri)?.name
            }
        }
        viewModel.openDocument.observe(viewLifecycleOwner) {
            openDocument(it)
        }

        initData()
    }

    private fun initData() {
        context?.let { viewModel.loadDirectory(it, uri) }
    }

    private fun openDocument(document: DocumentFile) {
        viewModel.isLoading.set(false)
        try {
            val openIntent = Intent(Intent.ACTION_VIEW).apply {
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                data = document.uri
            }
            startActivity(openIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context,
                resources.getString(R.string.error_no_activity, document.name),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}