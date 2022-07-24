package com.nauval.movieit.di

import com.nauval.movieit.core.domain.usecase.IMovieUseCase
import com.nauval.movieit.core.domain.usecase.MovieInteractor
import com.nauval.movieit.detail.DetailViewModel
import com.nauval.movieit.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IMovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}