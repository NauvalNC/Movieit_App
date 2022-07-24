package com.nauval.movieit.core.data

import android.content.Context
import com.nauval.movieit.core.data.source.local.LocalDataSource
import com.nauval.movieit.core.data.source.remote.RemoteDataSource
import com.nauval.movieit.core.data.source.remote.network.ApiResponse
import com.nauval.movieit.core.data.source.remote.response.MovieResponse
import com.nauval.movieit.core.domain.model.Movie
import com.nauval.movieit.core.domain.repository.IMovieRepository
import com.nauval.movieit.core.util.AppExecutors
import com.nauval.movieit.core.util.DataMapper
import com.nauval.movieit.core.util.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val context: Context
) : IMovieRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getAllMovies().map { DataMapper.mapEntitiesToDomain(it) }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                Utils.isNetworkAvailable(context)

            override suspend fun loadResponse(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            override suspend fun saveResponse(data: List<MovieResponse>) {
                val entities = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(entities)
            }
        }.asFlowable()

    override fun searchMovies(title: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.searchMovies(title).map { DataMapper.mapEntitiesToDomain(it) }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                Utils.isNetworkAvailable(context)

            override suspend fun loadResponse(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.searchMovies(title)

            override suspend fun saveResponse(data: List<MovieResponse>) {
                val entities = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(entities)
            }
        }.asFlowable()

    override fun getFavoriteMovie(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovie().map { DataMapper.mapEntitiesToDomain(it) }

    override fun setFavoriteMovie(movie: Movie, isFavorite: Boolean) {
        appExecutors.diskIO.execute {
            localDataSource.setFavoriteMovie(
                DataMapper.mapDomainToEntity(movie), isFavorite
            )
        }
    }
}