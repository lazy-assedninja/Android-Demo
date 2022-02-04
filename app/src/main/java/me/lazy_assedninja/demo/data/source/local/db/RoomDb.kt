package me.lazy_assedninja.demo.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.lazy_assedninja.demo.vo.User
import me.lazy_assedninja.demo.vo.YouBike

/**
 * Main database description.
 */
@Database(
    entities = [User::class, YouBike::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun youBikeDao(): YouBikeDao
}