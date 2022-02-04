package me.lazy_assedninja.demo.application

import android.app.Application
import me.lazy_assedninja.demo.BuildConfig
import me.lazy_assedninja.demo.di.AppComponent
import me.lazy_assedninja.demo.di.DaggerAppComponent
import timber.log.Timber

/**
 * Class for maintaining global application state.
 */
class DemoApplication : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {

        // Creates an instance of AppComponent using its Factory constructor
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}