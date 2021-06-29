package me.lazy_assedninja.sample.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import me.lazy_assedninja.sample.ui.index.MainComponent
import javax.inject.Singleton

// Scope annotation that the AppComponent uses Classes annotated with @Singleton
// will have a unique instance in this Component
@Singleton
// Definition of a Dagger component that adds info from the different modules to the graph
@Component(
    modules = [AppModule::class,
        SubComponentModule::class,
        StorageModule::class,
        DispatcherModule::class]
)
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
}