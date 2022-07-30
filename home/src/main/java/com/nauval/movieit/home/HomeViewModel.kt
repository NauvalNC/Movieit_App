package com.nauval.movieit.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nauval.movieit.core.domain.usecase.IMovieUseCase

class HomeViewModel(movieUseCase: IMovieUseCase) : ViewModel() {
    val lastMovies = movieUseCase.getAllMovies().asLiveData()
}