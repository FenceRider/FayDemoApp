package com.example.faydemo.network

import com.example.faydemo.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import timber.log.Timber

/**
 * Constructs a client that can be used with any api, not just the fay api
 */
fun makeOpenClient(): HttpClient = HttpClient(CIO) {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        }
        level = LogLevel.ALL
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = false
                ignoreUnknownKeys = true
            }
        )
    }
}

sealed class HTTPStatus {
    object InvalidAuthorization : HTTPStatus() // credentials are invalid
    object Forbidden : HTTPStatus() // credentials are fine, but resource is restricted
    object ServerError : HTTPStatus()
    object ClientError : HTTPStatus()
    object SerializationError : HTTPStatus()
    object UnknownError : HTTPStatus()
    object Success : HTTPStatus()
    object RateLimit : HTTPStatus()
}

sealed class FayHTTPResponse<T> {
    data class NetworkSuccess<T>(
        val response: HttpResponse,
        val body: T?,
        val httpError: HTTPStatus
    ) : FayHTTPResponse<T>()

    data class NetworkFailure<T>(val message: String = "", val exception: Exception) :
        FayHTTPResponse<T>()

    data class InternalFailure<T>(val message: String = "", val exception: Exception) :
        FayHTTPResponse<T>()
}

suspend inline fun <reified T> doRequest(request: suspend () -> HttpResponse): FayHTTPResponse<T> {
    var httpResponse: FayHTTPResponse.NetworkSuccess<T>? = null
    return try {
        val response = request()
        val httpError = when (response.status.value) {
            Constants.HTTP_Success -> HTTPStatus.Success
            in Constants.HTTP_ServerError -> HTTPStatus.ServerError

            in Constants.HTTP_ClientError -> when (response.status.value) {
                Constants.HTTP_Forbidden -> HTTPStatus.Forbidden
                Constants.HTTP_InvalidAuthorization -> HTTPStatus.InvalidAuthorization
                Constants.HTTP_TOO_MANY_REQUESTS -> HTTPStatus.RateLimit

                else -> HTTPStatus.ClientError
            }

            else -> HTTPStatus.UnknownError
        }

        httpResponse = FayHTTPResponse.NetworkSuccess(
            response = response,
            body = null,
            httpError = httpError
        )

        httpResponse.copy(body = response.body<T>())

    } catch (e: IOException) {
        FayHTTPResponse.NetworkFailure(exception = e)
    } catch (e: SerializationException) {
        httpResponse?.copy(httpError = HTTPStatus.SerializationError)
            ?: FayHTTPResponse.InternalFailure(
                exception = e
            )
    } catch (e: NoTransformationFoundException) {
        httpResponse?.copy(httpError = HTTPStatus.SerializationError)
            ?: FayHTTPResponse.InternalFailure(
                exception = e
            )
    } catch (e: JsonConvertException) {
        httpResponse?.copy(httpError = HTTPStatus.SerializationError)
            ?: FayHTTPResponse.InternalFailure(
                exception = e
            )
    } catch (e: Exception) {
        FayHTTPResponse.InternalFailure(exception = e)
    }
}