package me.lazy_assedninja.demo.data.repository

import android.content.Context
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.library.test.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Demo types.
 */
@OpenForTesting
@Singleton
class DemoRepository @Inject constructor(private val context: Context) {

    fun getDemoList(): List<String> {
        return listOf(
            context.getString(R.string.navigation_saf_fragment),
            context.getString(R.string.navigation_room_fragment),
            context.getString(R.string.navigation_documents_provider_fragment),
            context.getString(R.string.navigation_retrofit_fragment)
        )
    }
}