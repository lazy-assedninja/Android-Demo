package me.lazy_assedninja.demo.data.repository

import me.lazy_assedninja.demo.data.source.local.storage.Storage
import me.lazy_assedninja.demo.library.test.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Storage instances.
 */
@OpenForTesting
@Singleton
class StorageRepository @Inject constructor(private val storage: Storage) {

    fun setString(key: String, value: String) = storage.setString(key, value)

    fun getString(key: String, default: String?): String? = storage.getString(key, default)
}