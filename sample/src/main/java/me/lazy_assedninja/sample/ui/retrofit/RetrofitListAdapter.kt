package me.lazy_assedninja.sample.ui.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import me.lazy_assedninja.library.ui.BaseListAdapter
import me.lazy_assedninja.library.utils.ExecutorUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.databinding.ItemYouBikeBinding
import me.lazy_assedninja.sample.vo.YouBike

class RetrofitListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    executorUtils: ExecutorUtils,
    private val youBikeClickCallback: ((YouBike) -> Unit)?
) : BaseListAdapter<YouBike, ItemYouBikeBinding>(
    executorUtils = executorUtils,
    diffCallback = object : DiffUtil.ItemCallback<YouBike>() {
        override fun areItemsTheSame(oldItem: YouBike, newItem: YouBike): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: YouBike, newItem: YouBike): Boolean {
            return oldItem.stationNo == newItem.stationNo
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemYouBikeBinding {
        val binding = DataBindingUtil.inflate<ItemYouBikeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_you_bike,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.youBike?.let {
                youBikeClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemYouBikeBinding, item: YouBike) {
        binding.youBike = item
    }
}