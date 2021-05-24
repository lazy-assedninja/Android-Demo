package me.lazy_assedninja.library.utils

import me.lazy_assedninja.library.BuildConfig
import me.lazy_assedninja.library.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class LogUtils @Inject constructor() : Log {
    private val isDeBug: Boolean = BuildConfig.DEBUG

    override fun v(tag: String?, msg: String) {
        if (!isDeBug) return
        android.util.Log.v(tag, msg)
    }

    override fun d(tag: String?, msg: String) {
        if (!isDeBug) return
        android.util.Log.d(tag, msg)
    }

    override fun i(tag: String?, msg: String) {
        if (!isDeBug) return
        android.util.Log.i(tag, msg)
    }

    override fun w(tag: String?, msg: String) {
        if (!isDeBug) return
        android.util.Log.w(tag, msg)
    }

    override fun e(tag: String?, msg: String) {
        if (!isDeBug) return
        android.util.Log.e(tag, msg)
    }
}