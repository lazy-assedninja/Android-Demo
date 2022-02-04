package me.lazy_assedninja.demo.ui.saf

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.data.EventObserver
import me.lazy_assedninja.demo.databinding.SafFragmentBinding
import me.lazy_assedninja.demo.library.ui.BaseFragment
import me.lazy_assedninja.demo.ui.index.MainActivity
import me.lazy_assedninja.demo.ui.saf.directory.DirectoryFragment
import me.lazy_assedninja.demo.library.util.AppExecutors
import me.lazy_assedninja.demo.util.autoCleared
import javax.inject.Inject

private const val MIME_TYPE_IMAGE = "image/*"

private const val ARG_DIRECTORY_URI = "directory_uri"

private const val TAG_DIRECTORY = "directory"

class SAFFragment : BaseFragment() {

    var binding by autoCleared<SafFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SAFViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var openDocument: ActivityResultLauncher<Array<String>>
    private lateinit var openDocumentTree: ActivityResultLauncher<Uri>

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
        binding = SafFragmentBinding.inflate(inflater, container, false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initActivityResult()
    }

    private fun initData() {
        viewModel.chooseFile.observe(viewLifecycleOwner, EventObserver {
            if (it) openDocument.launch(arrayOf(MIME_TYPE_IMAGE))
        })
        viewModel.chooseFolder.observe(viewLifecycleOwner, EventObserver {
            if (it) openDocumentTree.launch(null)
        })
    }

    private fun initActivityResult() {
        openDocument = registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            activity?.let { activity ->
                DocumentFile.fromSingleUri(activity, uri)?.let { document ->
                    openDocument(document)
                }
            }
        }
        openDocumentTree = registerForActivityResult(
            ActivityResultContracts.OpenDocumentTree()
        ) { uri ->
            activity?.let { activity ->
                activity.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                DirectoryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_DIRECTORY_URI, uri.toString())
                    }
                    show(activity.supportFragmentManager, TAG_DIRECTORY)
                }
            }
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
            Toast.makeText(
                context,
                resources.getString(R.string.error_no_activity, document.name),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}