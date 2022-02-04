package me.lazy_assedninja.demo.data.source.remote

import androidx.lifecycle.LiveData
import me.lazy_assedninja.demo.vo.YouBike
import retrofit2.http.GET

/**
 * REST API access points
 */
interface RetrofitService {

    @GET("dotapp/youbike/v2/youbike_immediate.json")
    fun observerYouBikes(): LiveData<ApiResponse<List<YouBike>>>
}