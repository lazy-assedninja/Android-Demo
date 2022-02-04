package me.lazy_assedninja.demo.ui.retrofit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.lazy_assedninja.demo.common.TestUtil.createYouBike
import me.lazy_assedninja.demo.ui.retrofit.detail.RetrofitDetailViewModel
import me.lazy_assedninja.demo.util.LiveDataUtil.getValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RetrofitDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RetrofitDetailViewModel

    @Before
    fun init() {
        viewModel = RetrofitDetailViewModel()
    }

    @Test
    fun testNull() {
        assertThat(viewModel.youBike, notNullValue())
    }

    @Test
    fun setYouBike() {
        val youBike = createYouBike(
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
        viewModel.setYouBike(youBike)

        assertThat(getValue(viewModel.youBike), `is`(youBike))
    }
}