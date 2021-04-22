package me.lazy_assedninja.sample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.lazy_assedninja.sample.vo.User

/**
 * Main database description.
 */
@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDb : RoomDatabase() {

    abstract fun userDao(): UserDao
}