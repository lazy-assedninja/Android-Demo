package me.lazy_assedninja.demo.data.source.remote

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()