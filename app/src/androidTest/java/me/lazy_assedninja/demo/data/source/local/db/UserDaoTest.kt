package me.lazy_assedninja.demo.data.source.local.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.TestUtil.createUser
import me.lazy_assedninja.demo.common.getOrAwaitValue
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest : DbTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val userID = 1L
    private val userName = "Lazy-assed Ninja"
    private val newUserName = "new Lazy-assed Ninja"
    private val userEmail = "henryhuang861219@gmail.com"
    private val userPassword = "123456"
    private val user = createUser(
        id = userID,
        name = userName,
        email = userEmail,
        password = userPassword
    )
    private val newUser = createUser(
        id = userID,
        name = newUserName,
        email = userEmail,
        password = userPassword
    )

    @Before
    fun init() = runTest {

        // Insert For Test
        db.userDao().insert(user)
    }

    @Test
    fun insertAndGet() = runTest {
        val loaded = db.userDao().getUser(userID)
        assertThat(loaded, `is`(notNullValue()))
        assertThat(loaded.name, `is`(userName))
        assertThat(loaded.email, `is`(userEmail))
        assertThat(loaded.password, `is`(userPassword))
    }

    @Test
    fun replaceWhenInsertOnConflictAndGet() = runTest {
        db.userDao().insert(newUser)

        val loadedReplacement = db.userDao().getUser(userID)
        assertThat(loadedReplacement, `is`(notNullValue()))
        assertThat(loadedReplacement.name, `is`(newUserName))
        assertThat(loadedReplacement.email, `is`(userEmail))
        assertThat(loadedReplacement.password, `is`(userPassword))
    }

    @Test
    fun updateAndGet() = runTest {
        db.userDao().update(newUser)

        val loadedReplacement = db.userDao().getUser(userID)
        assertThat(loadedReplacement, `is`(notNullValue()))
        assertThat(loadedReplacement.name, `is`(newUserName))
        assertThat(loadedReplacement.email, `is`(userEmail))
        assertThat(loadedReplacement.password, `is`(userPassword))
    }

    @Test
    fun deleteAndCheck() = runTest {
        db.userDao().delete(user)

        val loaded = db.userDao().getUser(userID)
        assertThat(loaded, `is`(nullValue()))
    }

    @Test
    fun insertAndGetLiveDataOfAllUsers() {
        val users = db.userDao().observerUsers().getOrAwaitValue()
        assertThat(users.size, `is`(1))
        assertThat(users[0].name, `is`(userName))
        assertThat(users[0].email, `is`(userEmail))
        assertThat(users[0].password, `is`(userPassword))
    }
}