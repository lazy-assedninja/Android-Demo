package me.lazy_assedninja.demo.ui.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.lazy_assedninja.demo.data.Event
import me.lazy_assedninja.demo.data.Status
import me.lazy_assedninja.demo.domain.demo.GetDemoList
import me.lazy_assedninja.demo.ui.demo.di.DemoScope
import javax.inject.Inject

@DemoScope
class DemoViewModel @Inject constructor(
    private val getDemoList: GetDemoList
) : ViewModel() {

    private val _openDemo = MutableLiveData<Event<String>>()
    val openDemo: LiveData<Event<String>>
        get() = _openDemo

    private val _demos = MutableLiveData<List<String>>().apply { value = emptyList() }
    val demos: LiveData<List<String>>
        get() = _demos

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    init {
        loadDemos()
    }

    private fun loadDemos() {
        _isLoading.value = true

        viewModelScope.launch {
            val demoResult = getDemoList()

            if (demoResult.status == Status.SUCCESS) {
                _demos.value = demoResult.data
            }
            _isLoading.value = false
        }
    }

    fun openDemo(demoName: String) {
        _openDemo.value = Event(demoName)
    }
}