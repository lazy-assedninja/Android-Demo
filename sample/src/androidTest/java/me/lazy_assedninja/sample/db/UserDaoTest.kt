package me.lazy_assedninja.sample.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.lazy_assedninja.sample.common.TestUtil
import me.lazy_assedninja.sample.common.getOrAwaitValue
import me.lazy_assedninja.sample.vo.User
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.nullValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest : DbTest() {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var userID = 1L
    private var userName = "Lazy-assed Ninja"
    private var replaceUserName = "Replacement"
    private lateinit var testUser: User
    private lateinit var testReplacement: User

    @Before
    fun init() {
        testUser = TestUtil.createUser(
            id = userID,
            name = userName,
            email = "henryhuang861219@gmail.com",
            password = "123456"
        )
        testReplacement = TestUtil.createUser(
            id = userID,
            name = replaceUserName,
            email = "henryhuang861219@gmail.com",
            password = "123456"
        )

        // Insert For Test
        db.userDao().insertUsers(testUser)

        val loaded = db.userDao().getUser(userID).getOrAwaitValue()
        assertThat(loaded.name, `is`(userName))
    }

    @Test
    fun insertAndGet() {
        db.userDao().insertUsers(testReplacement)

        val loadedReplacement = db.userDao().getUser(userID).getOrAwaitValue()
        assertThat(loadedReplacement.name, `is`(replaceUserName))
    }

    @Test
    fun updateAndGet() {
        db.userDao().updateUsers(testReplacement)

        val loadedReplacement = db.userDao().getUser(userID).getOrAwaitValue()
        assertThat(loadedReplacement.name, `is`(replaceUserName))
    }

    @Test
    fun deleteAndCheck() {
        db.userDao().deleteUsers(testUser)

        val loaded = db.userDao().getUser(userID).getOrAwaitValue()
        assertThat(loaded, nullValue())
    }

    @Test
    fun loadAllUsers() {
        val users = db.userDao().loadAllUsers().getOrAwaitValue()
        assertThat(users.size, `is`(1))
    }
}