package com.nauval.movieit.core.domain.usecase

import com.nauval.movieit.core.data.Resource
import com.nauval.movieit.core.domain.model.Movie
import com.nauval.movieit.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : IMovieUseCase {
    override fun getAllMovies(): Flow<Resource<List<Movie>>> = movieRepository.getAllMovies()
    override fun searchMovies(title: String): Flow<Resource<List<Movie>>> =
        movieRepository.searchMovies(title)

    override fun getFavoriteMovie(): Flow<List<Movie>> = movieRepository.getFavoriteMovie()
    override fun setFavoriteMovie(movie: Movie, isFavorite: Boolean) =
        movieRepository.setFavoriteMovie(movie, isFavorite)
}