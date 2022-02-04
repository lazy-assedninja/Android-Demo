package me.lazy_assedninja.demo.domain.retrofit

import androidx.lifecycle.LiveData
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.repository.RetrofitRepository
import me.lazy_assedninja.demo.library.test.OpenForTesting
import me.lazy_assedninja.demo.ui.retrofit.di.RetrofitScope
import me.lazy_assedninja.demo.vo.YouBike
import javax.inject.Inject

@OpenForTesting
@RetrofitScope
class GetYouBikeList @Inject constructor(private val retrofitRepository: RetrofitRepository) {

    operator fun invoke(): LiveData<Resource<List<YouBike>>> = retrofitRepository.getYouBikeList()
}