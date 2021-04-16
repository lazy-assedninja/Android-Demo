package me.lazy_assedninja.sample.ui.saf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.DiffUtil
import me.lazy_assedninja.library.ui.BaseListAdapter
import me.lazy_assedninja.library.utils.ExecutorUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.databinding.ItemDirectoryBinding

class DirectoryListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    executorUtils: ExecutorUtils,
    private val directoryClickCallback: ((DocumentFile) -> Unit)?
) : BaseListAdapter<DocumentFile, ItemDirectoryBinding>(
    executorUtils = executorUtils,
    diffCallback = object : DiffUtil.ItemCallback<DocumentFile>() {
        override fun areItemsTheSame(oldItem: DocumentFile, newItem: DocumentFile): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DocumentFile, newItem: DocumentFile): Boolean {
            return oldItem.name == newItem.name
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemDirectoryBinding {
        val binding = DataBindingUtil.inflate<ItemDirectoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_directory,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.document?.let {
                directoryClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemDirectoryBinding, item: DocumentFile) {
        binding.document = item
        binding.entryImage.setImageResource(
            if (item.isDirectory) R.drawable.ic_folder_black_24dp
            else R.drawable.ic_file_black_24dp
        )
    }
}