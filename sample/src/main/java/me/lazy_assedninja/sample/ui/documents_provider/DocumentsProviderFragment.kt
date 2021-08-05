package me.lazy_assedninja.sample.ui.documents_provider

import android.content.Context
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import me.lazy_assedninja.library.ui.BaseFragment
import me.lazy_assedninja.library.utils.LogUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.databinding.FragmentDocumentsProviderBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared
import javax.inject.Inject

private const val AUTHORITY = "me.lazy_assedninja.documents"

class DocumentsProviderFragment : BaseFragment() {

    var binding by autoCleared<FragmentDocumentsProviderBinding>()

    @Inject
    lateinit var logUtils: LogUtils

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DocumentsProviderViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Grabs the mainComponent from the Activity and injects this Fragment
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_documents_provider,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchDocumentsProvider.isChecked = viewModel.getDocumentsProviderIsOpened()
        binding.switchDocumentsProvider.setOnCheckedChangeListener { _, isChecked ->
            viewModel.openDocumentsProvider(isChecked)

            activity?.contentResolver?.notifyChange(
                DocumentsContract.buildRootsUri(AUTHORITY),
                null
            )
        }

        initNavigationUI()
    }

    private fun initNavigationUI() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }
}