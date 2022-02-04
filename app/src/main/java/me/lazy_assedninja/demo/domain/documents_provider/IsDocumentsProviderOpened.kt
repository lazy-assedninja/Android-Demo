package me.lazy_assedninja.demo.domain.documents_provider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.repository.StorageRepository
import me.lazy_assedninja.demo.di.IODispatcher
import me.lazy_assedninja.demo.library.test.OpenForTesting
import me.lazy_assedninja.demo.ui.documents_provider.di.DocumentsProviderScope
import javax.inject.Inject

@OpenForTesting
@DocumentsProviderScope
class IsDocumentsProviderOpened @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val storageRepository: StorageRepository
) {

    suspend operator fun invoke(): Resource<Boolean> = withContext(dispatcher) {
        val isOpened = storageRepository.getString("documents_provider", "false")
        if (isOpened.isNullOrEmpty()) {
            Resource.error("SharedPreferences get string error.", false)
        } else {
            Resource.success(isOpened.toBoolean())
        }
    }
}