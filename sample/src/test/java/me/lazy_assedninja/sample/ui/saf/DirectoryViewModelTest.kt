package me.lazy_assedninja.sample.ui.saf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.lazy_assedninja.sample.common.mock
import me.lazy_assedninja.sample.utils.MainCoroutineScopeRule
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DirectoryViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineScope = MainCoroutineScopeRule()

    private var documentFile = mock(DocumentFile::class.java)
    private var viewModel = DirectoryViewModel()
    private val observer: Observer<DocumentFile> = mock()

    @Test
    fun testNull() {
        assertThat(viewModel.isLoading, notNullValue())
        assertThat(viewModel.openDirectory, notNullValue())
        assertThat(viewModel.openDocument, notNullValue())
        assertThat(viewModel.documents, notNullValue())
    }

    @Test
    fun documentClickedWhenIsDirectory() {
        `when`(
            documentFile.isDirectory
        ).thenReturn(true)
        viewModel.openDirectory.observeForever(observer)

        viewModel.documentClicked(documentFile)
        verify(observer).onChanged(documentFile)
        assertThat(viewModel.isLoading.get(), CoreMatchers.`is`(true))
    }

    @Test
    fun documentClickedWhenNotDirectory() {
        `when`(
            documentFile.isDirectory
        ).thenReturn(false)
        viewModel.openDocument.observeForever(observer)

        viewModel.documentClicked(documentFile)
        verify(observer).onChanged(documentFile)
        assertThat(viewModel.isLoading.get(), CoreMatchers.`is`(true))
    }
}