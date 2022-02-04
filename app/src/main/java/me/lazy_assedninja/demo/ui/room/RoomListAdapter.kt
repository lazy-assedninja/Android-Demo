package me.lazy_assedninja.demo.ui.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.databinding.RoomItemBinding
import me.lazy_assedninja.demo.library.ui.BaseListAdapter
import me.lazy_assedninja.demo.library.util.AppExecutors
import me.lazy_assedninja.demo.vo.User

class RoomListAdapter(
    appExecutors: AppExecutors,
    private val viewModel: RoomViewModel
) : BaseListAdapter<User, RoomItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {

    override fun createBinding(parent: ViewGroup): RoomItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.room_item,
            parent,
            false
        )
    }

    override fun bind(binding: RoomItemBinding, item: User) {
        binding.user = item
        binding.viewModel = viewModel
    }
}