package com.example.faydemo.utils

object Constants {
    const val HTTP_Success = 200
    val HTTP_ServerError = 500..599
    val HTTP_ClientError = 400..499
    const val HTTP_Forbidden = 403
    const val HTTP_InvalidAuthorization = 401
    const val HTTP_TOO_MANY_REQUESTS = 429

    const val DEFAULT_PROTO = "https://"
}