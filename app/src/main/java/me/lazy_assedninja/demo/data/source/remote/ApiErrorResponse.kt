package me.lazy_assedninja.demo.data.source.remote

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()