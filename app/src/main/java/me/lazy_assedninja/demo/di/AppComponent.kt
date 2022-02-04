package me.lazy_assedninja.demo.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import me.lazy_assedninja.demo.ui.index.MainComponent
import javax.inject.Singleton

/**
 * Definition of a Dagger component, create a graph that add dependencies from the different modules.
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        AppSubComponentModule::class,
        ViewModelModule::class,
        LibraryModule::class,
        StorageModule::class,
        DispatcherModule::class
    ]
)
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
}