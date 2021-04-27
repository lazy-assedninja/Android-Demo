package me.lazy_assedninja.sample.utils

import android.content.Context
import javax.inject.Inject

private const val PREFERENCES_DEMO = "Demo"

class SharedPreferencesStorage @Inject constructor(context: Context) : Storage {

    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCES_DEMO, Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            commit()
        }
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }
}