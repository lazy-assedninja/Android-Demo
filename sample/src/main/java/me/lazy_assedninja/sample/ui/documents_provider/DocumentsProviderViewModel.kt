package me.lazy_assedninja.sample.ui.documents_provider

import androidx.lifecycle.ViewModel
import me.lazy_assedninja.sample.utils.Storage
import javax.inject.Inject

private const val DOCUMENT_PROVIDER = "documents_provider"

class DocumentsProviderViewModel @Inject constructor(private val storage: Storage) : ViewModel() {

    fun getDocumentsProviderIsOpened(): Boolean {
        return storage.getString(DOCUMENT_PROVIDER).toBoolean()
    }

    fun openDocumentsProvider(isOpened: Boolean) {
        storage.setString(DOCUMENT_PROVIDER, isOpened.toString())
    }
}