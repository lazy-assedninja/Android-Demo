package me.lazy_assedninja.demo.ui.documents_provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.domain.documents_provider.IsDocumentsProviderOpened
import me.lazy_assedninja.demo.domain.documents_provider.OpenOrCloseDocumentsProvider
import me.lazy_assedninja.demo.util.LiveDataUtil.getValue
import org.hamcrest.CoreMatchers.`is`
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
class DocumentsProviderViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val isOpened = true
    private val isDocumentsProviderOpened = mock<IsDocumentsProviderOpened>()
    private val openOrCloseDocumentsProvider = mock<OpenOrCloseDocumentsProvider>()

    private lateinit var viewModel: DocumentsProviderViewModel

    @Before
    fun init() {
        viewModel = DocumentsProviderViewModel(
            isDocumentsProviderOpened,
            openOrCloseDocumentsProvider
        )
    }

    @Test
    fun loadOpenStatusSuccessWhenInit() = runTest {
        `when`(isDocumentsProviderOpened()).thenReturn(Resource.success(isOpened))

        withContext(mainCoroutineRule.dispatcher) {
            verify(isDocumentsProviderOpened).invoke()
            assertThat(getValue(viewModel.isOpen), `is`(isOpened))
        }
    }

    @Test
    fun loadOpenStatusErrorWhenInit() = runTest {
        val message = "Error."
        `when`(isDocumentsProviderOpened()).thenReturn(Resource.error(message, isOpened))

        withContext(mainCoroutineRule.dispatcher) {
            verify(isDocumentsProviderOpened).invoke()
            assertThat(getValue(viewModel.isOpen), `is`(isOpened))
            assertThat(getValue(viewModel.snackBarText).peekContent(), `is`(message))
        }
    }
}