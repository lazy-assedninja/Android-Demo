package me.lazy_assedninja.demo.ui.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.TestUtil.createUser
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.domain.room.InsertUser
import me.lazy_assedninja.demo.ui.room.add.AddRoomViewModel
import me.lazy_assedninja.demo.util.LiveDataUtil.getValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AddRoomViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val insertUser = mock<InsertUser>()

    private lateinit var viewModel: AddRoomViewModel

    @Before
    fun init() {
        viewModel = AddRoomViewModel(insertUser)
    }

    @Test
    fun checkUserData() {
        viewModel.checkUserData()

        assertThat(getValue(viewModel.checkUserData).peekContent(), `is`(Unit))
    }

    @Test
    fun addUser() = runTest {
        val name = "Lazy-assed Ninja"
        val email = "henryhuang861219@gmail.com"
        val password = "123456"
        val user = createUser(
            name = "Lazy-assed Ninja",
            email = "henryhuang861219@gmail.com",
            password = "123456"
        )
        viewModel.addUser(name, email, password)

        assertThat(getValue(viewModel.dismissKeyboard).peekContent(), `is`(Unit))
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(insertUser).invoke(user)
            assertThat(getValue(viewModel.isLoading), `is`(false))
            assertThat(getValue(viewModel.insertFinished).peekContent(), `is`(Unit))
        }
    }
}