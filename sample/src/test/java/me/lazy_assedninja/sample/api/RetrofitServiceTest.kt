package me.lazy_assedninja.sample.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URI_YOU_BIKE = "https://tcgbusfs.blob.core.windows.net/"

@RunWith(JUnit4::class)
class RetrofitServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: RetrofitService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    @Test
    fun getYouBikeList() {
        enqueueResponse("list_youbike.json")
        val list = (service.getYouBikeList().execute()).body()!!

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/dotapp/youbike/v2/youbike_immediate.json"))

        assertThat(list.size, `is`(287))

        val station1 = list[0]
        assertThat(station1.stationName, `is`("YouBike2.0_捷運科技大樓站"))

        val station2 = list[1]
        assertThat(station2.stationName, `is`("YouBike2.0_復興南路二段273號前"))
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api_response/$fileName")
            .source()
            .buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(inputStream.readString(Charsets.UTF_8))
        )
    }
}