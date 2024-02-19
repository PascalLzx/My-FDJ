package com.lzx.myfdj.data.exception

data class NoInternetException(
    override val message: String?,
) : Exception(message)

class ApiException(
    override val message: String?,
    override val cause: Throwable? = null,
) : Exception(message, cause)

class UnexpectedException(
    override val message: String?,
    override val cause: Throwable? = null,
) : Exception(message, cause)
