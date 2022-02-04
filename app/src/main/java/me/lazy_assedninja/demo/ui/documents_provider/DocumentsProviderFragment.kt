package me.lazy_assedninja.demo.ui.documents_provider

import android.content.Context
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import me.lazy_assedninja.demo.data.EventObserver
import me.lazy_assedninja.demo.databinding.DocumentsProviderFragmentBinding
import me.lazy_assedninja.demo.library.ui.BaseFragment
import me.lazy_assedninja.demo.ui.index.MainActivity
import me.lazy_assedninja.demo.util.autoCleared
import javax.inject.Inject

private const val AUTHORITY = "me.lazy_assedninja.demo.documents"

class DocumentsProviderFragment : BaseFragment() {

    var binding by autoCleared<DocumentsProviderFragmentBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DocumentsProviderViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Creates an instance of DocumentsProviderFragment by grabbing the factory from the
        // MainComponent and injects this fragment
        (activity as MainActivity).mainComponent.documentsProviderComponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DocumentsProviderFragmentBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
    }

    private fun initData() {
        viewModel.isOpen.observe(viewLifecycleOwner) {
            activity?.contentResolver?.notifyChange(
                DocumentsContract.buildRootsUri(AUTHORITY),
                null
            )
        }
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { message ->
            view?.let { view ->
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}