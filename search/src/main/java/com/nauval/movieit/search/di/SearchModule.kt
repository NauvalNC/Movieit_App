package com.nauval.movieit.search.di

import com.nauval.movieit.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel { SearchViewModel(get()) }
}