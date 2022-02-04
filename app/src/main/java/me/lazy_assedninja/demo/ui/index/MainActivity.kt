package me.lazy_assedninja.demo.ui.index

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.application.DemoApplication
import me.lazy_assedninja.demo.library.ui.BaseActivity

class MainActivity : BaseActivity() {

    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        initDagger()

        // When using activities, inject Dagger in the activity's onCreate() method
        // before calling super.onCreate() to avoid issues with fragment restoration.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initNavigation()
    }

    private fun initDagger() {
        // Creates an instance of main component by grabbing the factory from the app graph
        mainComponent = (application as DemoApplication).appComponent
            .mainComponent().create()

        // Injects this activity to the just created main component
        mainComponent.inject(this)
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupWithNavController(findViewById(R.id.toolbar), navController, appBarConfiguration)
    }
}