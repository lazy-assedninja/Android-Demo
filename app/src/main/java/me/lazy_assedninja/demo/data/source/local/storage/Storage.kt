package me.lazy_assedninja.demo.data.source.local.storage

/**
 * Used for provide [SharedPreferencesStorage] instance by Dagger.
 */
interface Storage {

    fun setString(key: String, value: String)

    fun getString(key: String, default: String?): String?
}