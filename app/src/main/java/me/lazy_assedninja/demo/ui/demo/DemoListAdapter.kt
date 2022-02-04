package me.lazy_assedninja.demo.ui.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.databinding.DemoItemBinding
import me.lazy_assedninja.demo.library.ui.BaseListAdapter
import me.lazy_assedninja.demo.library.util.AppExecutors

class DemoListAdapter(
    appExecutors: AppExecutors,
    private val viewModel: DemoViewModel
) : BaseListAdapter<String, DemoItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup): DemoItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.demo_item,
            parent,
            false
        )
    }

    override fun bind(binding: DemoItemBinding, item: String) {
        binding.demoName = item
        binding.viewModel = viewModel
    }
}