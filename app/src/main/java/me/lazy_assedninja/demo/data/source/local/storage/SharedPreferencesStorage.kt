package me.lazy_assedninja.demo.data.source.local.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Util of [SharedPreferences] interface.
 */
class SharedPreferencesStorage @Inject constructor(context: Context) : Storage {

    private val sharedPreferences = context.getSharedPreferences("demo", Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String, default: String?): String? {
        return sharedPreferences.getString(key, default)
    }
}