package com.nauval.movieit.core.data.source.remote

import com.nauval.movieit.core.data.source.remote.network.ApiResponse
import com.nauval.movieit.core.data.source.remote.network.ApiService
import com.nauval.movieit.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getAllMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getAllMovies()
                val data = response.results

                if (data.isNotEmpty()) emit(ApiResponse.Success(data))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchMovies(title: String): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.searchMovies(query = title)
                val data = response.results

                if (data.isNotEmpty()) emit(ApiResponse.Success(data))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}