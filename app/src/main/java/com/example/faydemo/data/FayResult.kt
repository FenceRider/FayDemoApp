package com.example.faydemo.data

sealed class FayResult<out T, out E> {
    data class Success<T>(val data: T?) : FayResult<T, Nothing>()
    data class Error<E>(val error: FayError, val data: E? = null) : FayResult<Nothing, E>()
}

sealed class FayError() {
    data object GenericError : FayError()
    data class ErrorMessage(val message: String) : FayError()
    data object NetworkFailure : FayError()
}