package com.lzx.myfdj.data.datasource

import com.lzx.myfdj.data.exception.ApiException
import com.lzx.myfdj.data.exception.NoInternetException
import com.lzx.myfdj.data.exception.UnexpectedException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRemoteDataSource<ResponseDataT, DomainDataMappedT>(private val dispatcher: CoroutineDispatcher) {

    suspend fun safeApiCall(
        apiCall: suspend () -> Response<ResponseDataT>,
        onSuccess: suspend (body: ResponseDataT) -> DomainDataMappedT,
    ): DomainDataMappedT {
        return withContext(dispatcher) {
            try {
                val response = apiCall.invoke()
                val body = response.body()
                // status is 2xx or 3xx
                if (response.isSuccessful && body != null) {
                    onSuccess(body)
                } else {
                    val code = response.code()
                    val errorBody = response.errorBody()?.string()
                    throw ApiException(message = "$code: $errorBody")
                }
            } catch (e: Exception) {
                throw when (e) {
                    is ApiException -> e
                    is IOException -> NoInternetException(message = e.message)
                    is HttpException -> {
                        val code = e.code()
                        ApiException(message = "$code: ${e.message}")
                    }

                    else -> {
                        UnexpectedException(message = e.message)
                    }
                }
            }
        }
    }
}
