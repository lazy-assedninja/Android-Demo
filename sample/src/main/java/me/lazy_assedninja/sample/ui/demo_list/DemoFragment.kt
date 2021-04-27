package me.lazy_assedninja.sample.ui.demo_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.lazy_assedninja.library.ui.BaseFragment
import me.lazy_assedninja.library.utils.ExecutorUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.binding.FragmentDataBindingComponent
import me.lazy_assedninja.sample.databinding.FragmentDemoBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared
import javax.inject.Inject

class DemoFragment : BaseFragment() {

    var binding by autoCleared<FragmentDemoBinding>()
    private var adapter by autoCleared<DemoListAdapter>()

    @Inject
    lateinit var executorUtils: ExecutorUtils

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DemoViewModel by viewModels {
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
            R.layout.fragment_demo,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DemoListAdapter(dataBindingComponent, executorUtils) {
            when (it.name) {
                getString(R.string.title_saf_demo) -> {
                    findNavController().navigate(
                        DemoFragmentDirections.toSAFFragment()
                    )
                }
                getString(R.string.title_room_demo) -> {
                    findNavController().navigate(
                        DemoFragmentDirections.toRoomFragment()
                    )
                }
                getString(R.string.title_documents_provider_demo) -> {
                    findNavController().navigate(
                        DemoFragmentDirections.toDocumentsProviderFragment()
                    )
                }
            }
        }
        binding.demoList.adapter = adapter

        initData()
    }

    private fun initData() {
        adapter.submitList(viewModel.loadDemo())
    }
}