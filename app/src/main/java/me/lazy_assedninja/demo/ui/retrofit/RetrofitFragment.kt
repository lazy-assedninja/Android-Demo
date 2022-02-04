package me.lazy_assedninja.demo.ui.retrofit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.lazy_assedninja.demo.data.EventObserver
import me.lazy_assedninja.demo.databinding.RetrofitFragmentBinding
import me.lazy_assedninja.demo.library.ui.BaseFragment
import me.lazy_assedninja.demo.ui.index.MainActivity
import me.lazy_assedninja.demo.library.util.AppExecutors
import me.lazy_assedninja.demo.util.autoCleared
import javax.inject.Inject

class RetrofitFragment : BaseFragment() {

    var binding by autoCleared<RetrofitFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RetrofitViewModel by viewModels {
        viewModelFactory
    }

    private var adapter by autoCleared<RetrofitListAdapter>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Creates an instance of RetrofitComponent by grabbing the factory from the MainComponent
        // and injects this fragment
        (activity as MainActivity).mainComponent.retrofitComponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RetrofitFragmentBinding.inflate(inflater, container, false).apply {
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
        adapter = RetrofitListAdapter(appExecutors, viewModel)
        binding.rv.adapter = adapter
    }

    private fun initData() {
        viewModel.openDetail.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(RetrofitFragmentDirections.toRetrofitDetailFragment(it))
        })
    }
}