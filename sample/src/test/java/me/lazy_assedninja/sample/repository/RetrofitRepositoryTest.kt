package me.lazy_assedninja.sample.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import me.lazy_assedninja.sample.api.RetrofitService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class RetrofitRepositoryTest {

    private val service = mock(RetrofitService::class.java)
    private lateinit var repository: RetrofitRepository

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repository = RetrofitRepository(service)
    }

    @Test
    fun loadYouBikeList() {
        repository.loadYouBikeList()
        verify(service).getYouBikeList()
    }
}