package me.lazy_assedninja.demo.data.repository

import android.content.Context
import me.lazy_assedninja.demo.common.mock
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt

@RunWith(JUnit4::class)
class DemoRepositoryTest {

    private val context = mock<Context>()

    private lateinit var repository: DemoRepository

    @Before
    fun init() {
        repository = DemoRepository(context)
    }

    @Test
    fun getDemoList() {
        val demo = "demo"
        `when`(context.getString(anyInt())).thenReturn(demo)

        val loaded = repository.getDemoList()
        assertThat(loaded.size, `is`(4))
        assertThat(loaded[0], `is`(demo))
    }
}