package me.lazy_assedninja.sample.ui.demo_list

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.lazy_assedninja.sample.repository.DemoRepository
import me.lazy_assedninja.sample.vo.Demo
import javax.inject.Inject

class DemoViewModel @Inject constructor(private val demoRepository: DemoRepository) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val demoList = MutableLiveData<List<Demo>>()

    fun loadDemoList() {
        isLoading.set(true)
        viewModelScope.launch {
            demoList.postValue(demoRepository.loadDemo())
        }
    }
}