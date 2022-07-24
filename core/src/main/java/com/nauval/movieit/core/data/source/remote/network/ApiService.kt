package com.nauval.movieit.core.data.source.remote.network

import com.nauval.movieit.core.BuildConfig
import com.nauval.movieit.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getAllMovies(@Query("api_key") apiKey: String = BuildConfig.API_KEY): ListMovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("query") query: String
    ): ListMovieResponse
}