package me.lazy_assedninja.demo.data.repository

import androidx.lifecycle.LiveData
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.source.local.db.RoomDb
import me.lazy_assedninja.demo.data.source.local.db.YouBikeDao
import me.lazy_assedninja.demo.data.source.remote.ApiResponse
import me.lazy_assedninja.demo.data.source.remote.RetrofitService
import me.lazy_assedninja.demo.library.test.OpenForTesting
import me.lazy_assedninja.demo.library.util.AppExecutors
import me.lazy_assedninja.demo.vo.YouBike
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Retrofit objects.
 */
@OpenForTesting
@Singleton
class RetrofitRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: RoomDb,
    private val youBikeDao: YouBikeDao,
    private val service: RetrofitService
) {

    fun getYouBikeList(): LiveData<Resource<List<YouBike>>> {
        return object : NetworkBoundResource<List<YouBike>, List<YouBike>>(appExecutors) {
            override fun shouldFetch(data: List<YouBike>?): Boolean = data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<List<YouBike>> = youBikeDao.observerYouBikes()

            override fun createCall(): LiveData<ApiResponse<List<YouBike>>> =
                service.observerYouBikes()

            override fun saveCallResult(item: List<YouBike>) {
                db.runInTransaction {
                    youBikeDao.deleteAll()
                    youBikeDao.insertAll(item)
                }
            }
        }.asLiveData()
    }
}