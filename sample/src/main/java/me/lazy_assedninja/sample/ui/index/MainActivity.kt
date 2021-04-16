package me.lazy_assedninja.sample.ui.index

import android.os.Bundle
import me.lazy_assedninja.library.ui.BaseActivity
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.application.MyApplication

class MainActivity : BaseActivity() {

    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        initDagger()

        // When using activities, inject Dagger in the activity's onCreate() method
        // before calling super.onCreate() to avoid issues with fragment restoration.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initDagger() {
        // Creates an instance of main component by grabbing the factory from the app graph
        mainComponent = (application as MyApplication).appComponent
            .mainComponent().create()

        // Injects this activity to the just created main component
        mainComponent.inject(this)
    }
}