package me.lazy_assedninja.demo.domain.demo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.repository.DemoRepository
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
class GetDemoListTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val demoRepository = mock<DemoRepository>()
    private lateinit var getDemoList: GetDemoList

    @Before
    fun init() {
        getDemoList = GetDemoList(mainCoroutineRule.dispatcher, demoRepository)
    }

    @Test
    fun invoke() = runTest {
        val list = listOf("demo")
        `when`(demoRepository.getDemoList()).thenReturn(list)

        val loaded = getDemoList.invoke()
        verify(demoRepository).getDemoList()
        assertThat(loaded, `is`(Resource.success(list)))
    }
}