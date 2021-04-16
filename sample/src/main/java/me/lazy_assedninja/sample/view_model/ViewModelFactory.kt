package me.lazy_assedninja.sample.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(viewmodelClass: Class<T>): T {
        val creator = creators[viewmodelClass] ?: creators.entries.firstOrNull {
            viewmodelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("Unknown ViewModel: $viewmodelClass.")
        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }
}