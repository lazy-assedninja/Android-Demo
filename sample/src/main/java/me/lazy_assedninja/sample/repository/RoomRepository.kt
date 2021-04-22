package me.lazy_assedninja.sample.repository

import androidx.lifecycle.LiveData
import me.lazy_assedninja.library.di.OpenForTesting
import me.lazy_assedninja.sample.db.UserDao
import me.lazy_assedninja.sample.vo.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Room instances.
 */
@Singleton
@OpenForTesting
class RoomRepository @Inject constructor(private val userDao: UserDao) {

    fun insertUsers(vararg users: User) = userDao.insertUsers(*users)

    fun updateUsers(vararg users: User) = userDao.updateUsers(*users)

    fun deleteUsers(vararg users: User) = userDao.deleteUsers(*users)

    fun getUser(id: Long): LiveData<User> = userDao.getUser(id)

    fun loadAllUsers(): LiveData<List<User>> = userDao.loadAllUsers()
}