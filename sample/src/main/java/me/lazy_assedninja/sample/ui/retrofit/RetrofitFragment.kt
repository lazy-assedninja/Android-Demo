package me.lazy_assedninja.sample.ui.retrofit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.lazy_assedninja.library.ui.BaseFragment
import me.lazy_assedninja.library.utils.ExecutorUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.binding.FragmentDataBindingComponent
import me.lazy_assedninja.sample.databinding.FragmentRetrofitBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared
import javax.inject.Inject

class RetrofitFragment : BaseFragment() {

    var binding by autoCleared<FragmentRetrofitBinding>()
    private var adapter by autoCleared<RetrofitListAdapter>()

    @Inject
    lateinit var executorUtils: ExecutorUtils

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RetrofitViewModel by viewModels {
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
            R.layout.fragment_retrofit,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RetrofitListAdapter(dataBindingComponent, executorUtils) {
            findNavController().navigate(RetrofitFragmentDirections.toRetrofitDetailFragment(it))
        }
        binding.youBikeList.adapter = adapter

        viewModel.youBikeList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
            } else {
                Toast.makeText(context, R.string.error_no_data, Toast.LENGTH_SHORT).show()
            }

            viewModel.isLoading.set(false)
        }

        initData()
    }

    private fun initData() {
        viewModel.loadYouBikeList()
    }
}