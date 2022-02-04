package me.lazy_assedninja.demo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import me.lazy_assedninja.demo.common.TestUtil.createYouBike
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.source.local.db.RoomDb
import me.lazy_assedninja.demo.data.source.local.db.YouBikeDao
import me.lazy_assedninja.demo.data.source.remote.RetrofitService
import me.lazy_assedninja.demo.util.ApiUtil.successCall
import me.lazy_assedninja.demo.util.InstantAppExecutors
import me.lazy_assedninja.demo.vo.YouBike
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class RetrofitRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val db = mock<RoomDb>()
    private val youBikeDao = mock<YouBikeDao>()
    private val service = mock<RetrofitService>()

    private lateinit var repository: RetrofitRepository

    @Before
    fun init() {
        `when`(db.youBikeDao()).thenReturn(youBikeDao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = RetrofitRepository(InstantAppExecutors(), db, youBikeDao, service)
    }

    @Test
    fun loadYouBikeFromNetwork() {
        val dbData = MutableLiveData<List<YouBike>>()
        `when`(youBikeDao.observerYouBikes()).thenReturn(dbData)

        val youBikes = listOf(
            createYouBike(
                1,
                "500101001",
                "YouBike2.0_捷運科技大樓站",
                "28",
                "17",
                "大安區",
                "2000-00-00 00:00:00",
                "25.02605",
                "121.5436",
                "復興南路二段235號前",
                "6",
                "1",
                "2000-00-00 00:00:00",
                "2000-00-00 00:00:00",
                "2000-00-00 00:00:00",
                "2000-00-00 00:00:00"
            )
        )
        val call = successCall(youBikes)
        `when`(service.observerYouBikes()).thenReturn(call)

        val data = repository.getYouBikeList()
        verify(youBikeDao).observerYouBikes()
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<YouBike>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        verify(observer).onChanged(Resource.loading(null))

        val updatedDbData = MutableLiveData<List<YouBike>>()
        `when`(youBikeDao.observerYouBikes()).thenReturn(updatedDbData)

        dbData.postValue(null)
        verify(service).observerYouBikes()
        verify(youBikeDao).deleteAll()
        verify(youBikeDao).insertAll(youBikes)

        updatedDbData.postValue(youBikes)
        verify(observer).onChanged(Resource.success(youBikes))
    }
}