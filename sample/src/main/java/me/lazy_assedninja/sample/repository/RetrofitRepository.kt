package me.lazy_assedninja.sample.repository

import me.lazy_assedninja.library.di.OpenForTesting
import me.lazy_assedninja.sample.api.RetrofitService
import me.lazy_assedninja.sample.vo.YouBike
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Retrofit objects.
 */
@Singleton
@OpenForTesting
class RetrofitRepository @Inject constructor(private val retrofitService: RetrofitService) {

    fun loadYouBikeList(): Call<List<YouBike>> = retrofitService.getYouBikeList()
}