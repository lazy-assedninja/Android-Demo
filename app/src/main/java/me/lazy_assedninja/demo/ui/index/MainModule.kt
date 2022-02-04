package me.lazy_assedninja.demo.ui.index

import dagger.Module
import me.lazy_assedninja.demo.ui.demo.di.DemoComponent
import me.lazy_assedninja.demo.ui.documents_provider.di.DocumentsProviderComponent
import me.lazy_assedninja.demo.ui.retrofit.di.RetrofitComponent
import me.lazy_assedninja.demo.ui.room.di.RoomComponent
import me.lazy_assedninja.demo.ui.saf.di.SAFComponent

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Module(
    subcomponents = [
        DemoComponent::class,
        SAFComponent::class,
        RoomComponent::class,
        DocumentsProviderComponent::class,
        RetrofitComponent::class
    ]
)
class MainModule