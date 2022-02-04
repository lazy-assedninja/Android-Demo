package me.lazy_assedninja.demo.ui.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import me.lazy_assedninja.demo.data.Event
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.domain.retrofit.GetYouBikeList
import me.lazy_assedninja.demo.ui.retrofit.di.RetrofitScope
import me.lazy_assedninja.demo.util.AbsentLiveData
import me.lazy_assedninja.demo.vo.YouBike
import javax.inject.Inject

@RetrofitScope
class RetrofitViewModel @Inject constructor(
    private val getYouBikeList: GetYouBikeList
) : ViewModel() {

    private val _loadYouBike = MutableLiveData(false)

    val youBikes: LiveData<Resource<List<YouBike>>> = _loadYouBike.switchMap {
        if (it) {
            getYouBikeList()
        } else {
            AbsentLiveData.create()
        }
    }

    private val _openDetail = MutableLiveData<Event<YouBike>>()
    val openDetail: LiveData<Event<YouBike>>
        get() = _openDetail

    init {
        loadYouBikes()
    }

    fun loadYouBikes() {
        _loadYouBike.value = true
    }

    fun openDetail(youBike: YouBike) {
        _openDetail.value = Event(youBike)
    }
}