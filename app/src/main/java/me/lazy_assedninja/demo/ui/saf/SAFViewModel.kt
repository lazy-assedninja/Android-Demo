package me.lazy_assedninja.demo.ui.saf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.lazy_assedninja.demo.data.Event
import me.lazy_assedninja.demo.ui.saf.di.SAFScope
import javax.inject.Inject

@SAFScope
class SAFViewModel @Inject constructor() : ViewModel() {

    private val _chooseFile = MutableLiveData<Event<Boolean>>()
    val chooseFile: LiveData<Event<Boolean>>
        get() = _chooseFile

    private val _chooseFolder = MutableLiveData<Event<Boolean>>()
    val chooseFolder: LiveData<Event<Boolean>>
        get() = _chooseFolder

    fun chooseFile() {
        _chooseFile.value = Event(true)
    }

    fun chooseFolder() {
        _chooseFolder.value = Event(true)
    }
}