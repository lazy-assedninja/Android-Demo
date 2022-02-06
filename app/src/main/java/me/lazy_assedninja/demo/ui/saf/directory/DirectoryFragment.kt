package me.lazy_assedninja.demo.ui.saf.directory

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.data.EventObserver
import me.lazy_assedninja.demo.databinding.DirectoryFragmentBinding
import me.lazy_assedninja.demo.library.util.AppExecutors
import me.lazy_assedninja.demo.ui.index.MainActivity
import me.lazy_assedninja.demo.util.autoCleared
import javax.inject.Inject

private const val ARG_DIRECTORY_URI = "directory_uri"

class DirectoryFragment : BottomSheetDialogFragment() {

    var binding by autoCleared<DirectoryFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DirectoryViewModel by viewModels {
        viewModelFactory
    }

    private var adapter by autoCleared<DirectoryListAdapter>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Creates an instance of SAFComponent by grabbing the factory from the MainComponent
        // and injects this fragment
        (activity as MainActivity).mainComponent.safComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DirectoryFragmentBinding.inflate(inflater, container, false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    private fun initView() {
        adapter = DirectoryListAdapter(appExecutors, viewModel)
        binding.directoryList.adapter = adapter
    }

    private fun initData() {
        context?.let { context ->
            arguments?.getString(ARG_DIRECTORY_URI)?.toUri()?.let { uri ->
                DocumentFile.fromTreeUri(context, uri)?.let {
                    binding.toolbar.title = it.name

                    viewModel.setDocumentFile(it)
                }
            }

            viewModel.openDirectory.observe(viewLifecycleOwner, EventObserver {
                DocumentFile.fromTreeUri(context, it.uri)?.let { childDocuments ->
                    binding.toolbar.title = childDocuments.name

                    viewModel.setDocumentFile(childDocuments)
                }
            })
            viewModel.openDocument.observe(viewLifecycleOwner, EventObserver {
                openDocument(it)
            })
        }
    }

    private fun openDocument(document: DocumentFile) {
        try {
            val openIntent = Intent(Intent.ACTION_VIEW).apply {
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                data = document.uri
            }
            startActivity(openIntent)
        } catch (ex: ActivityNotFoundException) {
            view?.let {
                Snackbar.make(
                    it,
                    getString(R.string.error_no_activity, document.name),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}