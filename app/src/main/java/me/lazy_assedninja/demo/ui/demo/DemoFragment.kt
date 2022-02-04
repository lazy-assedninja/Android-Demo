package me.lazy_assedninja.demo.ui.demo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.data.EventObserver
import me.lazy_assedninja.demo.databinding.DemoFragmentBinding
import me.lazy_assedninja.demo.library.ui.BaseFragment
import me.lazy_assedninja.demo.ui.index.MainActivity
import me.lazy_assedninja.demo.library.util.AppExecutors
import me.lazy_assedninja.demo.util.autoCleared
import javax.inject.Inject

class DemoFragment : BaseFragment() {

    var binding by autoCleared<DemoFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DemoViewModel by viewModels {
        viewModelFactory
    }

    private var adapter by autoCleared<DemoListAdapter>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Creates an instance of DemoComponent by grabbing the factory from the MainComponent
        // and injects this fragment
        (activity as MainActivity).mainComponent.demoComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DemoFragmentBinding.inflate(inflater, container, false).apply {
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
        adapter = DemoListAdapter(appExecutors, viewModel)
        binding.rv.adapter = adapter
    }

    private fun initData() {
        viewModel.openDemo.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                getString(R.string.navigation_saf_fragment) -> {
                    findNavController().navigate(
                        DemoFragmentDirections.toSAFFragment()
                    )
                }
                getString(R.string.navigation_room_fragment) -> {
                    findNavController().navigate(
                        DemoFragmentDirections.toRoomFragment()
                    )
                }
                getString(R.string.navigation_documents_provider_fragment) -> {
                    findNavController().navigate(
                        DemoFragmentDirections.toDocumentsProviderFragment()
                    )
                }
                getString(R.string.navigation_retrofit_fragment) -> {
                    findNavController().navigate(
                        DemoFragmentDirections.toRetrofitFragment()
                    )
                }
            }
        })
    }
}