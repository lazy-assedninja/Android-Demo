package me.lazy_assedninja.demo.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import me.lazy_assedninja.demo.view_model.ViewModelFactory

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}