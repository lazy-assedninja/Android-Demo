package me.lazy_assedninja.demo.ui.saf.directory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.DiffUtil
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.databinding.DirectoryItemBinding
import me.lazy_assedninja.demo.library.ui.BaseListAdapter
import me.lazy_assedninja.demo.library.util.AppExecutors

class DirectoryListAdapter(
    appExecutors: AppExecutors,
    private val viewModel: DirectoryViewModel
) : BaseListAdapter<DocumentFile, DirectoryItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<DocumentFile>() {
        override fun areItemsTheSame(oldItem: DocumentFile, newItem: DocumentFile): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DocumentFile, newItem: DocumentFile): Boolean {
            return oldItem.name == newItem.name
        }
    }
) {

    override fun createBinding(parent: ViewGroup): DirectoryItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.directory_item,
            parent,
            false
        )
    }

    override fun bind(binding: DirectoryItemBinding, item: DocumentFile) {
        binding.document = item
        binding.viewModel = viewModel

        binding.entryImage.setImageResource(
            if (item.isDirectory) R.drawable.ic_folder_black_24dp
            else R.drawable.ic_file_black_24dp
        )
    }
}