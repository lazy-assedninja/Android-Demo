package me.lazy_assedninja.demo.data.source.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import me.lazy_assedninja.demo.vo.User

/**
 * Interface for database access for User related operations.
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: Long): User

    @Query("SELECT * FROM users")
    fun observerUsers(): LiveData<List<User>>
}