package me.lazy_assedninja.demo.ui.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.TestUtil.createUser
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.domain.room.GetUser
import me.lazy_assedninja.demo.domain.room.UpdateUser
import me.lazy_assedninja.demo.ui.room.detail.RoomDetailViewModel
import me.lazy_assedninja.demo.util.LiveDataUtil.getValue
import me.lazy_assedninja.demo.vo.User
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RoomDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val userID = 1L
    private val name = "Lazy-assed Ninja"
    private val email = "henryhuang861219@gmail.com"
    private val password = "123456"
    private val user = createUser(
        id = 1L,
        name = "Lazy-assed Ninja",
        email = "henryhuang861219@gmail.com",
        password = "123456"
    )
    private val updateUser = mock<UpdateUser>()
    private val getUser = mock<GetUser>()

    private lateinit var viewModel: RoomDetailViewModel

    @Before
    fun init() {
        viewModel = RoomDetailViewModel(updateUser, getUser)
    }

    @Test
    fun testNull() = runTest {
        assertThat(viewModel.user, notNullValue())
        verify(getUser, never()).invoke(anyLong())
    }

    @Test
    fun sendSuccessResultToUI() = runTest {
        `when`(getUser.invoke(userID)).thenReturn(Resource.success(user))

        val observer = mock<Observer<User?>>()
        viewModel.user.observeForever(observer)
        viewModel.setUserID(userID)
        verify(observer, never()).onChanged(any())
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(observer).onChanged(user)
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun sendErrorResultToUI() = runTest {
        val message = "Error."
        `when`(getUser.invoke(userID)).thenReturn(Resource.error(message, user))

        val observer = mock<Observer<User?>>()
        viewModel.user.observeForever(observer)
        viewModel.setUserID(userID)
        verify(observer, never()).onChanged(any())
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(observer, never()).onChanged(any())
            assertThat(getValue(viewModel.snackBarText).peekContent(), `is`(message))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun loadUser() = runTest {
        `when`(getUser.invoke(userID)).thenReturn(Resource.success(user))
        viewModel.user.observeForever(mock())

        viewModel.setUserID(userID)

        withContext(mainCoroutineRule.dispatcher) {
            verify(getUser).invoke(userID)
            verifyNoMoreInteractions(getUser)
        }
    }

    @Test
    fun checkUserData() {
        viewModel.checkUserData()

        assertThat(getValue(viewModel.checkUserData).peekContent(), `is`(true))
    }

    @Test
    fun updateUser() = runTest {
        viewModel.setUserID(userID)
        viewModel.updateUser(name, email, password)

        assertThat(getValue(viewModel.dismissKeyboard).peekContent(), `is`(true))
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(updateUser).invoke(user)
            assertThat(getValue(viewModel.isLoading), `is`(false))
            assertThat(getValue(viewModel.updateFinished).peekContent(), `is`(true))
        }
    }
}