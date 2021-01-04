package com.zaen.githubuser.core.data

import com.zaen.githubuser.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class TransformResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(transformResponse(apiResponse.data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error<ResultType>("Empty Data"))
            }
            is ApiResponse.Error -> {
                onFetchFailed()
                emit(Resource.Error<ResultType>(apiResponse.errorMessage))
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun transformResponse(data: RequestType): ResultType

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}