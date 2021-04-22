package me.lazy_assedninja.sample.db

import androidx.lifecycle.LiveData
import androidx.room.*
import me.lazy_assedninja.sample.vo.User

/**
 * Interface for database access for User related operations.
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users: User)

    @Update
    fun updateUsers(vararg users: User)

    @Delete
    fun deleteUsers(vararg users: User)

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Long): LiveData<User>

    @Query("SELECT * FROM users")
    fun loadAllUsers(): LiveData<List<User>>
}