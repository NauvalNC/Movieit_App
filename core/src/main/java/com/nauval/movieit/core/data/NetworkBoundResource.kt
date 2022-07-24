package com.nauval.movieit.core.data

import com.nauval.movieit.core.data.source.remote.network.ApiResponse
import com.nauval.movieit.core.util.AppExecutors
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType>() {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())

        val dbSource = loadFromDB().first()

        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = loadResponse().first()) {
                is ApiResponse.Success -> {
                    saveResponse(apiResponse.data)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.message))
                }
            }
        } else emitAll(loadFromDB().map { Resource.Success(it) })
    }

    protected abstract fun loadFromDB(): Flow<ResultType>
    protected abstract suspend fun loadResponse(): Flow<ApiResponse<RequestType>>
    protected abstract suspend fun saveResponse(data: RequestType)
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected open fun onFetchFailed() {}
    fun asFlowable(): Flow<Resource<ResultType>> = result
}