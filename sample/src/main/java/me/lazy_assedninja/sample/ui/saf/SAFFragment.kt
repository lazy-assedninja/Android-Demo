package me.lazy_assedninja.sample.ui.saf

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.documentfile.provider.DocumentFile
import me.lazy_assedninja.library.ui.BaseFragment
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.binding.FragmentDataBindingComponent
import me.lazy_assedninja.sample.databinding.FragmentSafBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared

private const val MIME_TYPE_IMAGE = "image/*"

private const val REQUEST_CODE_FILE_PICKER = 1
private const val REQUEST_CODE_FOLDER_PICKER = 2

private const val ARG_DIRECTORY_URI = "directory_uri"

private const val TAG_DIRECTORY = "directory"

class SAFFragment : BaseFragment() {

    var binding by autoCleared<FragmentSafBinding>()

    private var dataBindingComponent = FragmentDataBindingComponent(this)

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
            R.layout.fragment_saf,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btChooseFile.setOnClickListener {
            startActivityForResult(
                Intent().apply {
                    action = Intent.ACTION_OPEN_DOCUMENT
                    type = MIME_TYPE_IMAGE
                    addCategory(Intent.CATEGORY_OPENABLE)
                }, REQUEST_CODE_FILE_PICKER
            )
        }
        binding.btChooseFolder.setOnClickListener {
            startActivityForResult(
                Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), REQUEST_CODE_FOLDER_PICKER
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (resultCode != Activity.RESULT_OK) return
        resultData?.data?.let { uri ->
            activity?.let { activity ->
                when (requestCode) {
                    REQUEST_CODE_FILE_PICKER -> {
                        DocumentFile.fromSingleUri(activity, uri)?.let { document ->
                            openDocument(document)
                        }
                    }
                    REQUEST_CODE_FOLDER_PICKER -> {
                        activity.contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        DirectoryBSDFragment().apply {
                            arguments = Bundle().apply {
                                putString(ARG_DIRECTORY_URI, uri.toString())
                            }
                            show(activity.supportFragmentManager, TAG_DIRECTORY)
                        }
                    }
                    else ->
                        Toast.makeText(activity, R.string.error_operating_error, Toast.LENGTH_LONG)
                            .show()
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