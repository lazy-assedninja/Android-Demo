package me.lazy_assedninja.sample.ui.demo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import me.lazy_assedninja.library.ui.BaseListAdapter
import me.lazy_assedninja.library.utils.ExecutorUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.databinding.ItemDemoBinding
import me.lazy_assedninja.sample.vo.Demo

class DemoListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    executorUtils: ExecutorUtils,
    private val demoClickCallback: ((Demo) -> Unit)?
) : BaseListAdapter<Demo, ItemDemoBinding>(
    executorUtils = executorUtils,
    diffCallback = object : DiffUtil.ItemCallback<Demo>() {
        override fun areItemsTheSame(oldItem: Demo, newItem: Demo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Demo, newItem: Demo): Boolean {
            return oldItem.name == newItem.name
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemDemoBinding {
        val binding = DataBindingUtil.inflate<ItemDemoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_demo,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.demo?.let {
                demoClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemDemoBinding, item: Demo) {
        binding.demo = item
    }
}