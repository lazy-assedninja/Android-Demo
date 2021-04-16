package me.lazy_assedninja.sample.ui.demo_list

import androidx.lifecycle.ViewModel
import me.lazy_assedninja.sample.repository.DemoRepository
import me.lazy_assedninja.sample.vo.Demo
import javax.inject.Inject

class DemoViewModel @Inject constructor(private val demoRepository: DemoRepository) : ViewModel() {

    fun loadDemo(): List<Demo> {
        return demoRepository.loadDemo()
    }
}