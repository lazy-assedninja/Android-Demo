package me.lazy_assedninja.sample.api

import me.lazy_assedninja.sample.vo.YouBike
import retrofit2.Call
import retrofit2.http.GET

/**
 * REST API access points
 */
interface RetrofitService {

    @GET("dotapp/youbike/v2/youbike_immediate.json")
    fun getYouBikeList(): Call<List<YouBike>>
}