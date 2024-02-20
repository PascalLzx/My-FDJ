package com.lzx.myfdj.data.datasource

import com.lzx.myfdj.data.exception.MyFdjException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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
                if (response.isSuccessful && body != null) {
                    onSuccess(body)
                } else {
                    val code = response.code()
                    val errorBody = response.errorBody()?.string()
                    throw MyFdjException(message = "$code: $errorBody")
                }
            } catch (error: Exception) {
                throw when (error) {
                    is HttpException -> {
                        val code = error.code()
                        MyFdjException(message = "$code: ${error.message}")
                    }

                    else -> MyFdjException(message = error.message, error)
                }
            }
        }
    }
}
