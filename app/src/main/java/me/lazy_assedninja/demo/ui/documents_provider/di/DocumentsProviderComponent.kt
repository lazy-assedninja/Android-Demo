package me.lazy_assedninja.demo.ui.documents_provider.di

import dagger.Subcomponent
import me.lazy_assedninja.demo.ui.documents_provider.DocumentsProviderFragment

/**
 * Components that inherit and extend the object graph of a parent component.
 */
@DocumentsProviderScope
@Subcomponent(modules = [DocumentsProviderModule::class])
interface DocumentsProviderComponent {

    // Factory to create instances of DocumentsProviderComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): DocumentsProviderComponent
    }

    // Classes that can be injected by this Component
    fun inject(documentsProviderFragment: DocumentsProviderFragment)
}