package me.lazy_assedninja.demo.domain.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.TestUtil.createUser
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.Status
import me.lazy_assedninja.demo.data.repository.RoomRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
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
class GetUserTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val roomRepository = mock<RoomRepository>()
    private lateinit var getUser: GetUser

    @Before
    fun init() {
        getUser = GetUser(mainCoroutineRule.dispatcher, roomRepository)
    }

    @Test
    fun invoke() = runTest {
        val userID = 1L
        val user = createUser(
            id = 1L,
            name = "Lazy-assed Ninja",
            email = "henryhuang861219@gmail.com",
            password = "123456"
        )
        `when`(roomRepository.getUser(userID)).thenReturn(user)

        val loaded = getUser.invoke(userID)
        verify(roomRepository).getUser(userID)
        assertThat(loaded, `is`(Resource.success(user)))
    }

    @Test
    fun exception() = runTest {
        val userID = 1L
        val exception = Exception("Exception.")
        `when`(roomRepository.getUser(userID)).thenThrow(exception)

        val loaded = getUser.invoke(userID)
        verify(roomRepository).getUser(userID)
        assertThat(loaded.status, `is`(Status.ERROR))
        assertThat(loaded.message, `is`(exception.toString()))
        assertThat(loaded.data, nullValue())
    }
}