package me.lazy_assedninja.sample.ui.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import me.lazy_assedninja.sample.common.TestUtil
import me.lazy_assedninja.sample.common.mock
import me.lazy_assedninja.sample.repository.RoomRepository
import me.lazy_assedninja.sample.vo.User
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RoomViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock(RoomRepository::class.java)
    private var viewModel = RoomViewModel(repository, TestCoroutineDispatcher())
    private lateinit var testUser: User

    @Before
    fun init() {
        testUser = TestUtil.createUser(
            id = 1L,
            name = "Lazy-assed Ninja",
            email = "henryhuang861219@gmail.com",
            password = "123456"
        )

        // Insert For Test
        viewModel.insertUsers(testUser)
    }

    @Test
    fun testNull() {
        assertThat(viewModel.isLoading, notNullValue())
        assertThat(viewModel.userID, notNullValue())
    }

    @Test
    fun insertUsers() {
        verify(repository).insertUsers(testUser)
    }

    @Test
    fun updateUsers() {
        viewModel.updateUsers(testUser)
        verify(repository).updateUsers(testUser)
    }

    @Test
    fun deleteUsers() {
        viewModel.deleteUsers(testUser)
        verify(repository).deleteUsers(testUser)
    }

    @Test
    fun getUser() {
        viewModel.getUser().observeForever(mock())

        viewModel.userID.postValue(1L)
        verify(repository).getUser(1L)
    }

    @Test
    fun loadAllUsers() {
        viewModel.loadAllUsers()
        verify(repository).loadAllUsers()
        assertThat(viewModel.isLoading.get(), `is`(true))
    }
}