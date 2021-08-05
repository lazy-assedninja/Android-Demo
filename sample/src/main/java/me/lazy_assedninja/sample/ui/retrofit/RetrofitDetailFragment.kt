package me.lazy_assedninja.sample.ui.retrofit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import me.lazy_assedninja.library.ui.BaseFragment
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.databinding.FragmentRetrofitDetailBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared

class RetrofitDetailFragment : BaseFragment() {

    var binding by autoCleared<FragmentRetrofitDetailBinding>()

    private val args by navArgs<RetrofitDetailFragmentArgs>()

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
            R.layout.fragment_retrofit_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.args = args.youBike

        initNavigationUI()
    }

    private fun initNavigationUI() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }
}