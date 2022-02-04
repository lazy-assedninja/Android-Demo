package me.lazy_assedninja.demo.ui.saf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.ui.saf.directory.DirectoryViewModel
import me.lazy_assedninja.demo.util.LiveDataUtil.getValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DirectoryViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DirectoryViewModel

    @Before
    fun init() {
        viewModel = DirectoryViewModel()
    }

    @Test
    fun testNull() {
        assertThat(viewModel.documents, notNullValue())
    }

    @Test
    fun sendResultToUI() {
        runTest {
            val mockDocumentFile = mock<DocumentFile>()
            val array = arrayOf<DocumentFile>()
            `when`(mockDocumentFile.listFiles()).thenReturn(array)
            val observer = mock<Observer<List<DocumentFile>>>()
            viewModel.documents.observeForever(observer)
            viewModel.setDocumentFile(mockDocumentFile)

            assertThat(getValue(viewModel.isLoading), `is`(true))

            withContext(mainCoroutineRule.dispatcher) {
                val childDocuments = mockDocumentFile.listFiles().toMutableList().apply {
                    sortBy {
                        it.name
                    }
                }.toList()
                verify(observer).onChanged(childDocuments)
                assertThat(getValue(viewModel.isLoading), `is`(false))
            }
        }
    }

    @Test
    fun documentClickThenOpenDocument() {
        val mockDocument = mock<DocumentFile>()
        `when`(mockDocument.isDirectory).thenReturn(false)
        viewModel.documentClick(mockDocument)

        assertThat(getValue(viewModel.openDocument).peekContent(), `is`(mockDocument))
    }

    @Test
    fun documentClickThenOpenDirectory() {
        val mockDocument = mock<DocumentFile>()
        `when`(mockDocument.isDirectory).thenReturn(true)
        viewModel.documentClick(mockDocument)

        assertThat(getValue(viewModel.openDirectory).peekContent(), `is`(mockDocument))
    }
}