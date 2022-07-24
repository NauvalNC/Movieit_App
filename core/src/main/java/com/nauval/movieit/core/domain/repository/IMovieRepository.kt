package com.nauval.movieit.core.domain.repository

import com.nauval.movieit.core.data.Resource
import com.nauval.movieit.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovies(): Flow<Resource<List<Movie>>>
    fun searchMovies(title: String): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, isFavorite: Boolean)
}