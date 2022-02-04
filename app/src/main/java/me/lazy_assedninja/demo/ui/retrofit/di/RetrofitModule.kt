package me.lazy_assedninja.demo.ui.retrofit.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.lazy_assedninja.demo.di.ViewModelKey
import me.lazy_assedninja.demo.ui.retrofit.RetrofitViewModel
import me.lazy_assedninja.demo.ui.retrofit.detail.RetrofitDetailViewModel

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
abstract class RetrofitModule {

    @Binds
    @IntoMap
    @ViewModelKey(RetrofitViewModel::class)
    abstract fun bindRetrofitViewModel(retrofitViewModel: RetrofitViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RetrofitDetailViewModel::class)
    abstract fun bindRetrofitDetailViewModel(retrofitDetailViewModel: RetrofitDetailViewModel):
            ViewModel
}