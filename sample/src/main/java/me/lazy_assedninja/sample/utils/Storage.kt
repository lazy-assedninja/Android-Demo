package me.lazy_assedninja.sample.utils

interface Storage {
    fun setString(key: String, value: String)
    fun getString(key: String): String
}