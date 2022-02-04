package me.lazy_assedninja.demo.ui.retrofit.di

import dagger.Subcomponent
import me.lazy_assedninja.demo.ui.retrofit.RetrofitFragment
import me.lazy_assedninja.demo.ui.retrofit.detail.RetrofitDetailFragment

/**
 * Components that inherit and extend the object graph of a parent component.
 */
@RetrofitScope
@Subcomponent(modules = [RetrofitModule::class])
interface RetrofitComponent {

    // Factory to create instances of RetrofitComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): RetrofitComponent
    }

    // Classes that can be injected by this Component
    fun inject(retrofitFragment: RetrofitFragment)
    fun inject(retrofitDetailFragment: RetrofitDetailFragment)
}