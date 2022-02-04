package me.lazy_assedninja.demo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.lazy_assedninja.demo.common.TestUtil.createUser
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.source.local.db.RoomDb
import me.lazy_assedninja.demo.data.source.local.db.UserDao
import me.lazy_assedninja.demo.vo.User
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RoomRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dao = mock<UserDao>()
    private lateinit var repository: RoomRepository

    private val testUser = createUser(
        id = 1L,
        name = "Lazy-assed Ninja",
        email = "henryhuang861219@gmail.com",
        password = "123456"
    )

    @Before
    fun init() {
        val db = mock<RoomDb>()
        `when`(db.userDao()).thenReturn(dao)
        repository = RoomRepository(dao)
    }

    @Test
    fun insertUsers() = runTest {
        repository.insert(testUser)

        verify(dao).insert(testUser)
    }

    @Test
    fun updateUsers() = runTest {
        repository.update(testUser)

        verify(dao).update(testUser)
    }

    @Test
    fun deleteUsers() = runTest {
        repository.delete(testUser)

        verify(dao).delete(testUser)
    }

    @Test
    fun getUser() = runTest {
        `when`(dao.getUser(1L)).thenReturn(testUser)
        repository.getUser(1L)

        verify(dao).getUser(1L)
    }

    @Test
    fun loadAllUsers() {
        val dbData = MutableLiveData<List<User>>()
        `when`(dao.observerUsers()).thenReturn(dbData)
        repository.observerUsers()

        verify(dao).observerUsers()
    }
}