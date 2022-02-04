package me.lazy_assedninja.demo.ui.documents_provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.lazy_assedninja.demo.data.Event
import me.lazy_assedninja.demo.data.Status
import me.lazy_assedninja.demo.domain.documents_provider.IsDocumentsProviderOpened
import me.lazy_assedninja.demo.domain.documents_provider.OpenOrCloseDocumentsProvider
import me.lazy_assedninja.demo.ui.documents_provider.di.DocumentsProviderScope
import javax.inject.Inject

@DocumentsProviderScope
class DocumentsProviderViewModel @Inject constructor(
    private val isDocumentsProviderOpened: IsDocumentsProviderOpened,
    private val openOrCloseDocumentsProvider: OpenOrCloseDocumentsProvider
) : ViewModel() {

    private val _isOpen = MutableLiveData(false)
    val isOpen: LiveData<Boolean>
        get() = _isOpen

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>>
        get() = _snackBarText

    init {
        loadOpenStatus()
    }

    private fun loadOpenStatus() {
        viewModelScope.launch {
            val isOpenResult = isDocumentsProviderOpened()

            _isOpen.value = isOpenResult.data
            if (isOpenResult.status == Status.ERROR) isOpenResult.message?.let {
                _snackBarText.value = Event(it)
            }
        }
    }

    fun changeOpenStatus() = viewModelScope.launch {
        _isOpen.value?.let {
            _isOpen.value = !it
            openOrCloseDocumentsProvider(!it)
        }
    }
}