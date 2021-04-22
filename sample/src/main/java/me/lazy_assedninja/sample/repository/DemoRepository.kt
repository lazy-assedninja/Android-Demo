package me.lazy_assedninja.sample.repository

import android.content.Context
import me.lazy_assedninja.library.di.OpenForTesting
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.vo.Demo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Utils instances.
 */
@Singleton
@OpenForTesting
class DemoRepository @Inject constructor(private val context: Context) {

    fun loadDemo(): List<Demo> {
        return listOf(
            Demo(context.getString(R.string.title_saf_demo)),
            Demo(context.getString(R.string.title_room_demo)),
        )
    }
}