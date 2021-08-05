package me.lazy_assedninja.sample.ui.demo_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.lazy_assedninja.sample.repository.DemoRepository
import me.lazy_assedninja.sample.utils.MainCoroutineScopeRule
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DemoViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineScope = MainCoroutineScopeRule()

    private val repository = Mockito.mock(DemoRepository::class.java)
    private var viewModel = DemoViewModel(repository)

    @Test
    fun testNull() {
        assertThat(viewModel.isLoading, notNullValue())
        assertThat(viewModel.demoList, notNullValue())
    }

    @Test
    fun getUtilsList() {
        viewModel.loadDemoList()
        verify(repository).loadDemo()
        assertThat(viewModel.isLoading.get(), `is`(true))
    }
}