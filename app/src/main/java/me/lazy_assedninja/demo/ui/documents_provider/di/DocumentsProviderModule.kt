package me.lazy_assedninja.demo.ui.documents_provider.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.lazy_assedninja.demo.di.ViewModelKey
import me.lazy_assedninja.demo.ui.documents_provider.DocumentsProviderViewModel

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
abstract class DocumentsProviderModule {

    @Binds
    @IntoMap
    @ViewModelKey(DocumentsProviderViewModel::class)
    abstract fun bindDocumentsProviderViewModel(
        documentsProviderViewModel: DocumentsProviderViewModel
    ): ViewModel
}