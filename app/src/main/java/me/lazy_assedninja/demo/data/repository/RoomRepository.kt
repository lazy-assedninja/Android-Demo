package me.lazy_assedninja.demo.data.repository

import androidx.lifecycle.LiveData
import me.lazy_assedninja.demo.data.source.local.db.UserDao
import me.lazy_assedninja.demo.library.test.OpenForTesting
import me.lazy_assedninja.demo.vo.User
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

/**
 * Repository that handles Room objects.
 */
@OpenForTesting
@Singleton
class RoomRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun insert(user: User) = userDao.insert(user)

    suspend fun update(user: User) = userDao.update(user)

    suspend fun delete(user: User) = userDao.delete(user)

    @Throws(Exception::class)
    suspend fun getUser(id: Long): User = userDao.getUser(id)

    @Throws(Exception::class)
    fun observerUsers(): LiveData<List<User>> = userDao.observerUsers()
}