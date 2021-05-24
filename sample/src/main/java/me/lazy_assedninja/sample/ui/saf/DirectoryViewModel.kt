package me.lazy_assedninja.sample.ui.saf

import android.content.Context
import android.net.Uri
import androidx.databinding.ObservableBoolean
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DirectoryViewModel @Inject constructor() : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val openDirectory: MutableLiveData<DocumentFile> = MutableLiveData()
    val openDocument: MutableLiveData<DocumentFile> = MutableLiveData()
    val documents: MutableLiveData<List<DocumentFile>> = MutableLiveData()

    fun loadDirectory(context: Context, uri: Uri) {
        val documentsTree = DocumentFile.fromTreeUri(context, uri) ?: return
        val childDocuments = documentsTree.listFiles().toMutableList()
        viewModelScope.launch {
            childDocuments.apply {
                sortBy {
                    it.name
                }
            }
            documents.postValue(childDocuments)
        }
    }

    /**
     * Method to dispatch between clicking on a document (which should be opened), and
     * a directory (which the user wants to navigate into).
     */
    fun documentClicked(documentFile: DocumentFile) {
        isLoading.set(true)
        if (documentFile.isDirectory) {
            openDirectory.postValue(documentFile)
        } else {
            openDocument.postValue(documentFile)
        }
    }
}