package me.lazy_assedninja.demo.ui.saf.directory

import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.*
import me.lazy_assedninja.demo.data.Event
import me.lazy_assedninja.demo.ui.saf.di.SAFScope
import javax.inject.Inject

@SAFScope
class DirectoryViewModel @Inject constructor() : ViewModel() {

    private val _documentFile = MutableLiveData<DocumentFile>()

    val documents = _documentFile.switchMap { documentsTree ->
        _isLoading.value = true
        liveData(viewModelScope.coroutineContext) {
            val childDocuments = documentsTree.listFiles().toMutableList().apply {
                sortBy {
                    it.name
                }
            }.toList()
            emit(childDocuments)
            _isLoading.value = false
        }
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val openDirectory = MutableLiveData<Event<DocumentFile>>()
    val openDocument = MutableLiveData<Event<DocumentFile>>()

    fun setDocumentFile(documentFile: DocumentFile) {
        _documentFile.value = documentFile
    }

    fun documentClick(documentFile: DocumentFile) {
        if (documentFile.isDirectory) {
            openDirectory.value = Event(documentFile)
        } else {
            openDocument.value = Event(documentFile)
        }
    }
}