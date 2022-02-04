package me.lazy_assedninja.demo.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var name: String = "",
    var email: String = "",
    var password: String = ""
)