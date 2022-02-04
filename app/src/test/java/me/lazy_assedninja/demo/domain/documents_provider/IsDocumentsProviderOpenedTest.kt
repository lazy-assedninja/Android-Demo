package me.lazy_assedninja.demo.domain.documents_provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.lazy_assedninja.demo.common.MainCoroutineRule
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.repository.StorageRepository
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
class IsDocumentsProviderOpenedTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val key = "documents_provider"
    private val default = "false"
    private val storageRepository = mock<StorageRepository>()
    private lateinit var isDocumentsProviderOpened: IsDocumentsProviderOpened

    @Before
    fun init() {
        isDocumentsProviderOpened =
            IsDocumentsProviderOpened(mainCoroutineRule.dispatcher, storageRepository)
    }

    @Test
    fun invoke() = runTest {
        val data = "true"
        `when`(storageRepository.getString(key, default)).thenReturn(data)

        val loaded = isDocumentsProviderOpened.invoke()
        verify(storageRepository).getString(key, default)
        assertThat(loaded, `is`(Resource.success(data.toBoolean())))
    }

    @Test
    fun dataIsNullOrEmpty() = runTest {
        val data = null
        `when`(storageRepository.getString(key, default)).thenReturn(data)

        val loaded = isDocumentsProviderOpened.invoke()
        val message = "SharedPreferences get string error."
        verify(storageRepository).getString(key, default)
        assertThat(loaded, `is`(Resource.error(message, false)))
    }
}