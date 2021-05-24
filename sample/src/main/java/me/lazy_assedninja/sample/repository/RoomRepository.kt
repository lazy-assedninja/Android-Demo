package me.lazy_assedninja.sample.repository

import androidx.lifecycle.LiveData
import me.lazy_assedninja.library.testing.OpenForTesting
import me.lazy_assedninja.sample.db.RoomDb
import me.lazy_assedninja.sample.db.UserDao
import me.lazy_assedninja.sample.vo.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Room instances.
 */
@Singleton
@OpenForTesting
class RoomRepository @Inject constructor(
    private val db: RoomDb,
    private val userDao: UserDao
) {

    fun insertUsers(vararg users: User) {
        db.runInTransaction {
            userDao.insertUsers(*users)
        }
    }

    fun updateUsers(vararg users: User) {
        db.runInTransaction {
            userDao.updateUsers(*users)
        }
    }

    fun deleteUsers(vararg users: User) {
        db.runInTransaction {
            userDao.deleteUsers(*users)
        }
    }

    fun getUser(id: Long): LiveData<User> = userDao.getUser(id)

    fun loadAllUsers(): LiveData<List<User>> = userDao.loadAllUsers()
}