package me.lazy_assedninja.demo.domain.retrofit

import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.repository.RetrofitRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class GetYouBikeListTest {

    private val retrofitRepository = mock<RetrofitRepository>()

    private lateinit var getYouBikeList: GetYouBikeList

    @Before
    fun init() {
        getYouBikeList = GetYouBikeList(retrofitRepository)
    }

    @Test
    fun invoke() {
        getYouBikeList.invoke()

        verify(retrofitRepository).getYouBikeList()
    }
}