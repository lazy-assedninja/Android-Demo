package me.lazy_assedninja.sample.ui.retrofit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.lazy_assedninja.library.utils.LogUtils
import me.lazy_assedninja.sample.common.mock
import me.lazy_assedninja.sample.repository.RetrofitRepository
import me.lazy_assedninja.sample.utils.MainCoroutineScopeRule
import me.lazy_assedninja.sample.vo.YouBike
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import retrofit2.Call

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RetrofitViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineScope = MainCoroutineScopeRule()

    private val call = mock<Call<List<YouBike>>>()
    private val repository = mock(RetrofitRepository::class.java)
    private var viewModel = RetrofitViewModel(LogUtils(), repository)

    @Test
    fun loadYouBikeList() {
        `when`(repository.loadYouBikeList()).thenReturn(call)
        viewModel.loadYouBikeList()
        verify(repository).loadYouBikeList()
        assertThat(viewModel.isLoading.get(), `is`(true))
    }
}