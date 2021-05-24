package me.lazy_assedninja.sample.ui.retrofit

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.lazy_assedninja.library.utils.LogUtils
import me.lazy_assedninja.sample.repository.RetrofitRepository
import me.lazy_assedninja.sample.vo.YouBike
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetrofitViewModel @Inject constructor(
    private val logUtils: LogUtils,
    private val retrofitRepository: RetrofitRepository
) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val youBikeList: MutableLiveData<List<YouBike>?> = MutableLiveData()

    fun loadYouBikeList() {
        isLoading.set(true)

        viewModelScope.launch {
            retrofitRepository.loadYouBikeList().enqueue(object : Callback<List<YouBike>> {
                override fun onResponse(
                    call: Call<List<YouBike>>,
                    response: Response<List<YouBike>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            youBikeList.postValue(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<YouBike>>, t: Throwable) {
                    youBikeList.postValue(null)
                    logUtils.e("RetrofitViewModel", "loadYouBikeList Error: ${t.message}")
                }
            })
        }
    }
}