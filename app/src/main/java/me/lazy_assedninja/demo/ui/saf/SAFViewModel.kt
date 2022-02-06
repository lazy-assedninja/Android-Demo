package me.lazy_assedninja.demo.ui.saf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.lazy_assedninja.demo.data.Event
import me.lazy_assedninja.demo.ui.saf.di.SAFScope
import javax.inject.Inject

@SAFScope
class SAFViewModel @Inject constructor() : ViewModel() {

    private val _chooseFile = MutableLiveData<Event<Unit>>()
    val chooseFile: LiveData<Event<Unit>>
        get() = _chooseFile

    private val _chooseFolder = MutableLiveData<Event<Unit>>()
    val chooseFolder: LiveData<Event<Unit>>
        get() = _chooseFolder

    fun chooseFile() {
        _chooseFile.value = Event(Unit)
    }

    fun chooseFolder() {
        _chooseFolder.value = Event(Unit)
    }
}