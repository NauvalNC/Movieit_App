package com.nauval.movieit.detail

import androidx.lifecycle.ViewModel
import com.nauval.movieit.core.domain.model.Movie
import com.nauval.movieit.core.domain.usecase.IMovieUseCase

class DetailViewModel(private val movieUseCase: IMovieUseCase) : ViewModel() {
    fun setFavoriteMovie(movie: Movie, isFavorite: Boolean) =
        movieUseCase.setFavoriteMovie(movie, isFavorite)
}