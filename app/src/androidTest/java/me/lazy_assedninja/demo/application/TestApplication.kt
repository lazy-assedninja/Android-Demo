package me.lazy_assedninja.demo.application

import android.app.Application
import me.lazy_assedninja.demo.BuildConfig
import me.lazy_assedninja.demo.DemoTestRunner
import timber.log.Timber

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See [DemoTestRunner].
 */
class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}