package me.lazy_assedninja.demo.ui.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.databinding.RetrofitItemBinding
import me.lazy_assedninja.demo.library.ui.BaseListAdapter
import me.lazy_assedninja.demo.library.util.AppExecutors
import me.lazy_assedninja.demo.vo.YouBike

class RetrofitListAdapter(
    appExecutors: AppExecutors,
    private val viewModel: RetrofitViewModel
) : BaseListAdapter<YouBike, RetrofitItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<YouBike>() {
        override fun areItemsTheSame(oldItem: YouBike, newItem: YouBike): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: YouBike, newItem: YouBike): Boolean {
            return oldItem.stationNo == newItem.stationNo
        }
    }
) {

    override fun createBinding(parent: ViewGroup): RetrofitItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.retrofit_item,
            parent,
            false
        )
    }

    override fun bind(binding: RetrofitItemBinding, item: YouBike) {
        binding.youBike = item
        binding.viewModel = viewModel
    }
}