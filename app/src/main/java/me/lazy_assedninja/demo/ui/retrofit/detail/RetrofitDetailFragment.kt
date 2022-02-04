package me.lazy_assedninja.demo.ui.retrofit.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import me.lazy_assedninja.demo.databinding.RetrofitDetailFragmentBinding
import me.lazy_assedninja.demo.library.ui.BaseFragment
import me.lazy_assedninja.demo.ui.index.MainActivity
import me.lazy_assedninja.demo.util.autoCleared
import javax.inject.Inject

class RetrofitDetailFragment : BaseFragment() {

    var binding by autoCleared<RetrofitDetailFragmentBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RetrofitDetailViewModel by viewModels {
        viewModelFactory
    }

    private val args by navArgs<RetrofitDetailFragmentArgs>()

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
        binding = RetrofitDetailFragmentBinding.inflate(
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
        viewModel.setYouBike(args.youBike)
    }
}