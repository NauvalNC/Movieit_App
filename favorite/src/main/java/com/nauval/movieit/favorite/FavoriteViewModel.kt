package com.nauval.movieit.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nauval.movieit.core.domain.usecase.IMovieUseCase

class FavoriteViewModel(movieUseCase: IMovieUseCase) : ViewModel() {
    val favoriteMovies = movieUseCase.getFavoriteMovie().asLiveData()
}