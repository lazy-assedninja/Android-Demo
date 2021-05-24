package me.lazy_assedninja.sample.ui.documents_provider

import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.lazy_assedninja.sample.utils.Storage
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.verify

private const val DOCUMENT_PROVIDER = "documents_provider"

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DocumentsProviderViewModelTest {

    private val storage = Mockito.mock(Storage::class.java)
    private var viewModel = DocumentsProviderViewModel(storage)

    @Test
    fun getDocumentsProviderIsOpened() {
        viewModel.getDocumentsProviderIsOpened()
        verify(storage).getString(DOCUMENT_PROVIDER)
    }

    @Test
    fun openDocumentsProvider() {
        viewModel.openDocumentsProvider(true)
        verify(storage).setString(DOCUMENT_PROVIDER, true.toString())
    }
}