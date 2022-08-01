package com.nauval.movieit.di

import com.nauval.movieit.core.domain.usecase.IMovieUseCase
import com.nauval.movieit.core.domain.usecase.MovieInteractor
import org.koin.dsl.module

val useCaseModule = module {
    factory<IMovieUseCase> { MovieInteractor(get()) }
}