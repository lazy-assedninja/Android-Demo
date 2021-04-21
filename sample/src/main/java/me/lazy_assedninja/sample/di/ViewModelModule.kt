package me.lazy_assedninja.sample.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.lazy_assedninja.sample.ui.demo_list.DemoViewModel
import me.lazy_assedninja.sample.ui.saf.DirectoryViewModel
import me.lazy_assedninja.sample.view_model.ViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DemoViewModel::class)
    abstract fun bindDemoViewModel(demoViewModel: DemoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DirectoryViewModel::class)
    abstract fun bindDirectoryViewModel(directoryViewModel: DirectoryViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}