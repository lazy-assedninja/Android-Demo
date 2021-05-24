package me.lazy_assedninja.sample.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import me.lazy_assedninja.sample.common.TestUtil
import me.lazy_assedninja.sample.db.RoomDb
import me.lazy_assedninja.sample.db.UserDao
import me.lazy_assedninja.sample.vo.User
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class RoomRepositoryTest {

    private val dao = mock(UserDao::class.java)
    private lateinit var repository: RoomRepository
    private lateinit var testUser: User

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(RoomDb::class.java)
        `when`(db.userDao()).thenReturn(dao)
        `when`(db.runInTransaction(any())).thenCallRealMethod()
        repository = RoomRepository(db, dao)
        testUser = TestUtil.createUser(
            id = 1L,
            name = "Lazy-assed Ninja",
            email = "henryhuang861219@gmail.com",
            password = "123456"
        )
    }

    @Test
    fun insertUsers() {
        repository.insertUsers(testUser)
        verify(dao).insertUsers(testUser)
    }

    @Test
    fun updateUsers() {
        repository.updateUsers(testUser)
        verify(dao).updateUsers(testUser)
    }

    @Test
    fun deleteUsers() {
        repository.deleteUsers(testUser)
        verify(dao).deleteUsers(testUser)
    }

    @Test
    fun getUser() {
        val dbData = MutableLiveData<User>()
        `when`(dao.getUser(1L)).thenReturn(dbData)

        repository.getUser(1L)
        verify(dao).getUser(1L)
    }

    @Test
    fun loadAllUsers() {
        val dbData = MutableLiveData<List<User>>()
        `when`(dao.loadAllUsers()).thenReturn(dbData)

        repository.loadAllUsers()
        verify(dao).loadAllUsers()
    }
}