package me.lazy_assedninja.demo.ui.saf.di

import dagger.Subcomponent
import me.lazy_assedninja.demo.ui.saf.SAFFragment
import me.lazy_assedninja.demo.ui.saf.directory.DirectoryFragment

/**
 * Components that inherit and extend the object graph of a parent component.
 */
@SAFScope
@Subcomponent(modules = [SAFModule::class])
interface SAFComponent {

    // Factory to create instances of SAFComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): SAFComponent
    }

    // Classes that can be injected by this Component
    fun inject(safFragment: SAFFragment)
    fun inject(directoryFragment: DirectoryFragment)
}