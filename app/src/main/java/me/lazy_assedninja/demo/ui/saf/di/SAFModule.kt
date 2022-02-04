package me.lazy_assedninja.demo.ui.saf.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.lazy_assedninja.demo.di.ViewModelKey
import me.lazy_assedninja.demo.ui.saf.SAFViewModel
import me.lazy_assedninja.demo.ui.saf.directory.DirectoryViewModel

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
abstract class SAFModule {

    @Binds
    @IntoMap
    @ViewModelKey(SAFViewModel::class)
    abstract fun bindSAFViewModel(safViewModel: SAFViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DirectoryViewModel::class)
    abstract fun bindDirectoryViewModel(directoryViewModel: DirectoryViewModel): ViewModel
}