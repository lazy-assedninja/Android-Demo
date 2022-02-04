package me.lazy_assedninja.demo.data.source.remote

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun success() {
        val apiResponse =
            ApiResponse.create<String>(Response.success("Success.")) as ApiSuccessResponse

        assertThat(apiResponse.body, `is`("Success."))
    }

    @Test
    fun error() {
        val errorResponse = Response.error<String>(
            400,
            "Error.".toResponseBody("application/txt".toMediaTypeOrNull())
        )
        val errorMessage =
            (ApiResponse.create<String>(errorResponse) as ApiErrorResponse).errorMessage

        assertThat(errorMessage, `is`("Error."))
    }

    @Test
    fun exception() {
        val exception = Exception("Exception.")
        val errorMessage = ApiResponse.create<String>(exception).errorMessage

        assertThat(errorMessage, `is`("Exception."))
    }
}