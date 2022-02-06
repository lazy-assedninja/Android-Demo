package me.lazy_assedninja.demo.domain.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import me.lazy_assedninja.demo.common.TestUtil.createUser
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.Status
import me.lazy_assedninja.demo.data.repository.RoomRepository
import me.lazy_assedninja.demo.util.LiveDataUtil.getValue
import me.lazy_assedninja.demo.vo.User
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class GetUserListLiveDataTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val roomRepository = mock<RoomRepository>()

    private lateinit var getUserListLiveData: GetUserListLiveData

    @Before
    fun init() {
        getUserListLiveData = GetUserListLiveData(roomRepository)
    }

    @Test
    fun invoke() {
        val list = listOf(
            createUser(
                id = 1L,
                name = "Lazy-assed Ninja",
                email = "henryhuang861219@gmail.com",
                password = "123456"
            )
        )
        val liveData = MutableLiveData(list)
        `when`(roomRepository.observerUsers()).thenReturn(liveData)

        val loaded = getUserListLiveData.invoke()
        verify(roomRepository).observerUsers()
        assertThat(getValue(loaded), `is`(Resource.success(list)))
    }

    @Test
    fun emptyData() {
        val list = MutableLiveData(emptyList<User>())
        `when`(roomRepository.observerUsers()).thenReturn(list)

        val loaded = getUserListLiveData.invoke()
        verify(roomRepository).observerUsers()
        assertThat(getValue(loaded).status, `is`(Status.ERROR))
        assertThat(getValue(loaded).message, `is`("No data."))
        assertThat(getValue(loaded).data?.size, `is`(0))
    }

    @Test
    fun exception() {
        val exception = Exception("Exception.")
        `when`(roomRepository.observerUsers()).thenThrow(exception)

        val loaded = getUserListLiveData.invoke()
        verify(roomRepository).observerUsers()
        assertThat(getValue(loaded).status, `is`(Status.ERROR))
        assertThat(getValue(loaded).message, `is`(exception.toString()))
        assertThat(getValue(loaded).data?.size, `is`(0))
    }
}