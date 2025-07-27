package com.example.faydemo.data

import com.example.faydemo.network.FayHTTPResponse
import com.example.faydemo.network.HTTPStatus

/**
 * totally optional intermediate step between network client and repository intended to reduce the
 * occurrences of boilerplate error handling
 *
 * @param response the result of a request
 * @return A result object containing the data or an error
 */
fun <T> consume(response: FayHTTPResponse<T>): FayResult<T, Nothing> {
    return when (response) {
        is FayHTTPResponse.InternalFailure -> FayResult.Error(FayError.GenericError)
        is FayHTTPResponse.NetworkFailure -> FayResult.Error(FayError.NetworkFailure)
        is FayHTTPResponse.NetworkSuccess -> {
            when (response.httpError) {
                HTTPStatus.ClientError,
                HTTPStatus.SerializationError,
                HTTPStatus.ServerError,
                HTTPStatus.UnknownError ->
                    FayResult.Error(FayError.GenericError)

                HTTPStatus.Forbidden,
                HTTPStatus.InvalidAuthorization ->
                    FayResult.Error(FayError.GenericError)

                HTTPStatus.Success -> FayResult.Success(response.body)
                HTTPStatus.RateLimit -> FayResult.Error(FayError.GenericError)
            }
        }
    }
}