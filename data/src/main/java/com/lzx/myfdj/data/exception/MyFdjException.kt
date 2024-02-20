package com.lzx.myfdj.data.exception

class MyFdjException(
    override val message: String?,
    override val cause: Throwable? = null,
) : Exception(message, cause)
