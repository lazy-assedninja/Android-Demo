package me.lazy_assedninja.demo.ui.demo.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.lazy_assedninja.demo.di.ViewModelKey
import me.lazy_assedninja.demo.ui.demo.DemoViewModel

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
abstract class DemoModule {

    @Binds
    @IntoMap
    @ViewModelKey(DemoViewModel::class)
    abstract fun bindDemoViewModel(demoViewModel: DemoViewModel): ViewModel
}