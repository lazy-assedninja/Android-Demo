package me.lazy_assedninja.demo.data.source.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.lazy_assedninja.demo.vo.YouBike

/**
 * Interface for database access for Retrofit related operations.
 */
@Dao
interface YouBikeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(youBikes: List<YouBike>)

    @Query("DELETE FROM you_bike")
    fun deleteAll()

    @Query("SELECT * FROM you_bike")
    fun observerYouBikes(): LiveData<List<YouBike>>
}