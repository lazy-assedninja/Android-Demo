package me.lazy_assedninja.demo.domain.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.TestUtil.createUser
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.repository.RoomRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class InsertUserTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val roomRepository = mock<RoomRepository>()

    private lateinit var insertUser: InsertUser

    @Before
    fun init() {
        insertUser = InsertUser(mainCoroutineRule.dispatcher, roomRepository)
    }

    @Test
    fun invoke() = runTest {
        val user = createUser(
            id = 1L,
            name = "Lazy-assed Ninja",
            email = "henryhuang861219@gmail.com",
            password = "123456"
        )
        insertUser.invoke(user)

        Mockito.verify(roomRepository).insert(user)
    }
}