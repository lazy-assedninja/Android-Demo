package me.lazy_assedninja.sample.ui.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import me.lazy_assedninja.library.ui.BaseListAdapter
import me.lazy_assedninja.library.utils.ExecutorUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.databinding.ItemUserBinding
import me.lazy_assedninja.sample.vo.User

class RoomListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    executorUtils: ExecutorUtils,
    private val userClickCallback: ((User) -> Unit)?,
    private val userLongClickCallback: ((User) -> Unit)?
) : BaseListAdapter<User, ItemUserBinding>(
    executorUtils = executorUtils,
    diffCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemUserBinding {
        val binding = DataBindingUtil.inflate<ItemUserBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_user,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.user?.let { user ->
                userClickCallback?.invoke(user)
            }
        }
        binding.root.setOnLongClickListener {
            binding.user?.let { user ->
                userLongClickCallback?.invoke(user)
            }
            return@setOnLongClickListener true
        }
        return binding
    }

    override fun bind(binding: ItemUserBinding, item: User) {
        binding.user = item
    }
}