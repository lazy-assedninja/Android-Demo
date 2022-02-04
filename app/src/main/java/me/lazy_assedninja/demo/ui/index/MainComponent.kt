package me.lazy_assedninja.demo.ui.index

import dagger.Subcomponent
import me.lazy_assedninja.demo.di.ActivityScope
import me.lazy_assedninja.demo.ui.demo.di.DemoComponent
import me.lazy_assedninja.demo.ui.documents_provider.di.DocumentsProviderComponent
import me.lazy_assedninja.demo.ui.retrofit.di.RetrofitComponent
import me.lazy_assedninja.demo.ui.room.di.RoomComponent
import me.lazy_assedninja.demo.ui.saf.di.SAFComponent

/**
 * Components that inherit and extend the object graph of a parent component.
 */
@ActivityScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    // Factory to create instances of MainComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun demoComponent(): DemoComponent.Factory
    fun safComponent(): SAFComponent.Factory
    fun roomComponent(): RoomComponent.Factory
    fun documentsProviderComponent(): DocumentsProviderComponent.Factory
    fun retrofitComponent(): RetrofitComponent.Factory

    // Classes that can be injected by this Component
    fun inject(activity: MainActivity)
}