package me.lazy_assedninja.demo.ui.demo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.domain.demo.GetDemoList
import me.lazy_assedninja.demo.util.LiveDataUtil.getValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DemoViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val demo1 = "demo1"
    private val demo2 = "demo2"
    private val demos = listOf(demo1, demo2)
    private val getDemoList = mock<GetDemoList>()

    private lateinit var viewModel: DemoViewModel

    @Before
    fun init() {
        runTest {
            `when`(getDemoList.invoke()).thenReturn(Resource.success(demos))
        }
        viewModel = DemoViewModel(getDemoList)
    }

    @Test
    fun loadDemosWhenViewModelInit() = runTest {
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(getDemoList).invoke()
            assertThat(getValue(viewModel.demos), `is`(demos))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun openDemo() {
        viewModel.openDemo(demo1)

        assertThat(getValue(viewModel.openDemo).peekContent(), `is`(demo1))
    }
}