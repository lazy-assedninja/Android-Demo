package me.lazy_assedninja.sample.application

import android.app.Application
import me.lazy_assedninja.sample.di.AppComponent
import me.lazy_assedninja.sample.di.DaggerAppComponent

class MyApplication : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        DaggerAppComponent.factory().create(applicationContext)
    }
}