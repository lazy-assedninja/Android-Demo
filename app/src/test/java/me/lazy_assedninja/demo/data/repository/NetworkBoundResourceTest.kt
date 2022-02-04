package me.lazy_assedninja.demo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import me.lazy_assedninja.demo.common.CountingAppExecutors
import me.lazy_assedninja.demo.common.mock
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.source.remote.ApiResponse
import me.lazy_assedninja.demo.util.ApiUtil.createCall
import me.lazy_assedninja.demo.util.InstantAppExecutors
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mockito.*
import retrofit2.Response
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

@RunWith(Parameterized::class)
class NetworkBoundResourceTest(private val useRealExecutors: Boolean) {

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun param() = arrayListOf(true, false)
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val fetchedOnce = AtomicBoolean(false)
    private lateinit var countingAppExecutors: CountingAppExecutors

    private val dbData = MutableLiveData<Ninja>()
    private lateinit var handleSaveCallResult: (Ninja) -> Unit
    private lateinit var handleShouldMatch: (Ninja?) -> Boolean
    private lateinit var handleCreateCall: () -> LiveData<ApiResponse<Ninja>>
    private lateinit var networkBoundResource: NetworkBoundResource<Ninja, Ninja>

    init {
        if (useRealExecutors) {
            countingAppExecutors = CountingAppExecutors()
        }
    }

    @Before
    fun init() {
        val appExecutors = if (useRealExecutors) countingAppExecutors.appExecutors
        else InstantAppExecutors()

        networkBoundResource = object : NetworkBoundResource<Ninja, Ninja>(appExecutors) {
            override fun saveCallResult(item: Ninja) {
                handleSaveCallResult(item)
            }

            override fun shouldFetch(data: Ninja?): Boolean {

                // Since test methods don't handle repetitive fetching, call it only once.
                return handleShouldMatch(data) && fetchedOnce.compareAndSet(false, true)
            }

            override fun loadFromDb(): LiveData<Ninja> {
                return dbData
            }

            override fun createCall(): LiveData<ApiResponse<Ninja>> {
                return handleCreateCall()
            }
        }
    }

    private fun drain() {
        if (!useRealExecutors) return

        try {
            countingAppExecutors.drainTasks(1, TimeUnit.SECONDS)
        } catch (t: Throwable) {
            throw AssertionError(t)
        }

    }

    @Test
    fun basicFromNetwork() {
        val saved = AtomicReference<Ninja>()
        handleShouldMatch = { it == null }
        val fetchedDbValue = Ninja(1)
        handleSaveCallResult = { ninja ->
            saved.set(ninja)
            dbData.setValue(fetchedDbValue)
        }
        val networkResult = Ninja(1)
        handleCreateCall = { createCall(Response.success(networkResult)) }

        val observer = mock<Observer<Resource<Ninja>>>()
        networkBoundResource.asLiveData().observeForever(observer)
        drain()
        verify(observer).onChanged(Resource.loading(null))
        reset(observer)
        dbData.value = null
        drain()
        assertThat(saved.get(), `is`(networkResult))
        verify(observer).onChanged(Resource.success(fetchedDbValue))
    }

    @Test
    fun failureFromNetwork() {
        val saved = AtomicBoolean(false)
        handleShouldMatch = { it == null }
        handleSaveCallResult = {
            saved.set(true)
        }
        val body = "Error.".toResponseBody("text/html".toMediaTypeOrNull())
        handleCreateCall = { createCall(Response.error(500, body)) }

        val observer = mock<Observer<Resource<Ninja>>>()
        networkBoundResource.asLiveData().observeForever(observer)
        drain()
        verify(observer).onChanged(Resource.loading(null))
        reset(observer)
        dbData.value = null
        drain()
        assertThat(saved.get(), `is`(false))
        verify(observer).onChanged(Resource.error("Error.", null))
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun dbSuccessWithoutNetwork() {
        val saved = AtomicBoolean(false)
        handleShouldMatch = { it == null }
        handleSaveCallResult = {
            saved.set(true)
        }

        val observer = mock<Observer<Resource<Ninja>>>()
        networkBoundResource.asLiveData().observeForever(observer)
        drain()
        verify(observer).onChanged(Resource.loading(null))
        reset(observer)
        val dbNinja = Ninja(1)
        dbData.value = dbNinja
        drain()
        verify(observer).onChanged(Resource.success(dbNinja))
        assertThat(saved.get(), `is`(false))
        val dbNinja2 = Ninja(2)
        dbData.value = dbNinja2
        drain()
        verify(observer).onChanged(Resource.success(dbNinja2))
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun dbSuccessWithFetchFailure() {
        val dbValue = Ninja(1)
        val saved = AtomicBoolean(false)
        handleShouldMatch = { ninja -> ninja === dbValue }
        handleSaveCallResult = {
            saved.set(true)
        }
        val body = "Error.".toResponseBody("text/html".toMediaTypeOrNull())
        val apiResponseLiveData = MutableLiveData<ApiResponse<Ninja>>()
        handleCreateCall = { apiResponseLiveData }

        val observer = mock<Observer<Resource<Ninja>>>()
        networkBoundResource.asLiveData().observeForever(observer)
        drain()
        verify(observer).onChanged(Resource.loading(null))
        reset(observer)

        dbData.value = dbValue
        drain()
        verify(observer).onChanged(Resource.loading(dbValue))

        apiResponseLiveData.value = ApiResponse.create(Response.error(400, body))
        drain()
        assertThat(saved.get(), `is`(false))
        verify(observer).onChanged(Resource.error("Error.", dbValue))

        val dbValue2 = Ninja(2)
        dbData.value = dbValue2
        drain()
        verify(observer).onChanged(Resource.error("Error.", dbValue2))
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun dbSuccessWithReFetchSuccess() {
        val dbValue = Ninja(1)
        val dbValue2 = Ninja(2)
        val saved = AtomicReference<Ninja>()
        handleShouldMatch = { ninja -> ninja === dbValue }
        handleSaveCallResult = { ninja ->
            saved.set(ninja)
            dbData.setValue(dbValue2)
        }
        val apiResponseLiveData = MutableLiveData<ApiResponse<Ninja>>()
        handleCreateCall = { apiResponseLiveData }

        val observer = mock<Observer<Resource<Ninja>>>()
        networkBoundResource.asLiveData().observeForever(observer)
        drain()
        verify(observer).onChanged(Resource.loading(null))
        reset(observer)

        dbData.value = dbValue
        drain()
        val networkResult = Ninja(1)
        verify(observer).onChanged(Resource.loading(dbValue))
        apiResponseLiveData.value = ApiResponse.create(Response.success(networkResult))
        drain()
        assertThat(saved.get(), `is`(networkResult))
        verify(observer).onChanged(Resource.success(dbValue2))
        verifyNoMoreInteractions(observer)
    }

    private data class Ninja(var value: Int)
}