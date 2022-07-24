package com.nauval.movieit.core.data.source.local

import com.nauval.movieit.core.data.source.local.entity.MovieEntity
import com.nauval.movieit.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {
    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()
    fun searchMovies(title: String): Flow<List<MovieEntity>> = movieDao.searchMovies(title)
    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()
    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovie(movies)
    fun setFavoriteMovie(movie: MovieEntity, isFavorite: Boolean) {
        movieDao.updateMovie(movie.also { it.isFavorite = isFavorite })
    }
}