package me.lazy_assedninja.sample.common

import me.lazy_assedninja.sample.vo.User

object TestUtil {

    fun createUser(id: Long, name: String, email: String, password: String) = User(
        id = id,
        name = name,
        email = email,
        password = password
    )
}