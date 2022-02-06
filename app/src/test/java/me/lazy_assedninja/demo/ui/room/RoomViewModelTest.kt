package me.lazy_assedninja.demo.ui.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.TestUtil.createUser
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.domain.room.DeleteUser
import me.lazy_assedninja.demo.domain.room.GetUserListLiveData
import me.lazy_assedninja.demo.util.LiveDataUtil.getValue
import me.lazy_assedninja.demo.vo.User
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RoomViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val user = createUser(
        id = 1L,
        name = "Lazy-assed Ninja",
        email = "henryhuang861219@gmail.com",
        password = "123456"
    )
    private val getUserListLiveData = mock<GetUserListLiveData>()
    private val deleteUser = mock<DeleteUser>()

    private lateinit var viewModel: RoomViewModel

    @Test
    fun testNull() {
        viewModel = RoomViewModel(getUserListLiveData, deleteUser)

        assertThat(viewModel.users, notNullValue())
    }

    @Test
    fun sendSuccessResultToUI() {
        val list = listOf(user)
        val resource = Resource.success(list)
        val liveData = MutableLiveData(resource)
        `when`(getUserListLiveData.invoke()).thenReturn(liveData)
        viewModel = RoomViewModel(getUserListLiveData, deleteUser)

        val loadingObserver = mock<Observer<Boolean>>()
        val usersObserver = mock<Observer<List<User>>>()
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.users.observeForever(usersObserver)
        verify(loadingObserver).onChanged(true)
        verify(usersObserver).onChanged(list)
        assertThat(getValue(viewModel.isLoading), `is`(false))
    }

    @Test
    fun sendErrorResultToUI() {
        val message = "Error."
        val list = listOf(user)
        val resource = Resource.error(message, list)
        val liveData = MutableLiveData(resource)
        `when`(getUserListLiveData.invoke()).thenReturn(liveData)
        viewModel = RoomViewModel(getUserListLiveData, deleteUser)

        val loadingObserver = mock<Observer<Boolean>>()
        val usersObserver = mock<Observer<List<User>>>()
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.users.observeForever(usersObserver)
        verify(loadingObserver).onChanged(true)
        verify(usersObserver).onChanged(list)
        assertThat(getValue(viewModel.snackBarText).peekContent(), `is`(message))
        assertThat(getValue(viewModel.isLoading), `is`(false))
        assertThat(getValue(viewModel.users), `is`(list))
    }

    @Test
    fun loadUsers() {
        val liveData = MutableLiveData<Resource<List<User>>>()
        `when`(getUserListLiveData.invoke()).thenReturn(liveData)
        viewModel = RoomViewModel(getUserListLiveData, deleteUser)
        viewModel.users.observeForever(mock())

        verify(getUserListLiveData).invoke()
        verifyNoMoreInteractions(getUserListLiveData)
    }

    @Test
    fun addUser() {
        viewModel = RoomViewModel(getUserListLiveData, deleteUser)
        viewModel.addUser()

        assertThat(getValue(viewModel.addUser).peekContent(), `is`(Unit))
    }

    @Test
    fun openDetail() {
        viewModel = RoomViewModel(getUserListLiveData, deleteUser)
        val userID = 1L
        viewModel.openDetail(userID)

        assertThat(getValue(viewModel.openDetail).peekContent(), `is`(userID))
    }

    @Test
    fun showDeleteDialog() {
        viewModel = RoomViewModel(getUserListLiveData, deleteUser)
        viewModel.showDeleteDialog(user)

        assertThat(getValue(viewModel.showDeleteDialog).peekContent(), `is`(user))
    }

    @Test
    fun deleteUser() = runTest {
        viewModel = RoomViewModel(getUserListLiveData, deleteUser)
        viewModel.deleteUser(user)

        withContext(mainCoroutineRule.dispatcher) {
            verify(deleteUser).invoke(user)
        }
    }
}