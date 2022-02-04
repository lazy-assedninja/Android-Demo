package me.lazy_assedninja.demo.ui.retrofit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.TestUtil.createYouBike
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.domain.retrofit.GetYouBikeList
import me.lazy_assedninja.demo.util.LiveDataUtil.getValue
import me.lazy_assedninja.demo.vo.YouBike
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RetrofitViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val youBike = createYouBike(
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
    private val getYouBikeList = mock<GetYouBikeList>()

    private lateinit var viewModel: RetrofitViewModel

    @Before
    fun init() {
        viewModel = RetrofitViewModel(getYouBikeList)
    }

    @Test
    fun testNullWhenInit() = runTest {
        assertThat(viewModel.youBikes, notNullValue())

        verify(getYouBikeList, never()).invoke()
    }

    @Test
    fun sendResultToUI() {
        val liveData = MutableLiveData<Resource<List<YouBike>>>()
        `when`(getYouBikeList.invoke()).thenReturn(liveData)

        val observer = mock<Observer<Resource<List<YouBike>>>>()
        viewModel.youBikes.observeForever(observer)
        viewModel.loadYouBikes()
        verify(observer, never()).onChanged(any())

        val youBikes = listOf(youBike)
        val resource = Resource.success(youBikes)
        liveData.value = resource
        verify(observer).onChanged(resource)
    }

    @Test
    fun loadYouBikesWhenInit() {
        viewModel.youBikes.observeForever(mock())

        verify(getYouBikeList).invoke()
        verifyNoMoreInteractions(getYouBikeList)
    }

    @Test
    fun openDetail() {
        assertThat(viewModel.openDetail, notNullValue())

        viewModel.openDetail(youBike)
        assertThat(getValue(viewModel.openDetail).peekContent(), `is`(youBike))
    }
}