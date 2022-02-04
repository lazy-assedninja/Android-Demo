package me.lazy_assedninja.demo.ui.retrofit.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.lazy_assedninja.demo.ui.retrofit.di.RetrofitScope
import me.lazy_assedninja.demo.vo.YouBike
import javax.inject.Inject

@RetrofitScope
class RetrofitDetailViewModel @Inject constructor() : ViewModel() {

    private val _youBike = MutableLiveData<YouBike>()
    val youBike: LiveData<YouBike>
        get() = _youBike

    fun setYouBike(youBike: YouBike) {
        _youBike.value = youBike
    }
}