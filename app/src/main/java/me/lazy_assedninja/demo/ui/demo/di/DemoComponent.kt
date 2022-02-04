package me.lazy_assedninja.demo.ui.demo.di

import dagger.Subcomponent
import me.lazy_assedninja.demo.ui.demo.DemoFragment

/**
 * Components that inherit and extend the object graph of a parent component.
 */
@DemoScope
@Subcomponent(modules = [DemoModule::class])
interface DemoComponent {

    // Factory to create instances of DemoComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): DemoComponent
    }

    // Classes that can be injected by this Component
    fun inject(demoFragment: DemoFragment)
}