package me.lazy_assedninja.demo.domain.documents_provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.repository.StorageRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class OpenOrCloseDocumentsProviderTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val storageRepository = mock<StorageRepository>()
    private lateinit var openOrCloseDocumentsProvider: OpenOrCloseDocumentsProvider

    @Before
    fun init() {
        openOrCloseDocumentsProvider =
            OpenOrCloseDocumentsProvider(mainCoroutineRule.dispatcher, storageRepository)
    }

    @Test
    fun invoke() = runTest {
        val data = true
        openOrCloseDocumentsProvider.invoke(data)

        verify(storageRepository).setString("documents_provider", data.toString())
    }
}