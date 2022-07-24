package com.nauval.movieit.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nauval.movieit.core.domain.usecase.IMovieUseCase

class SearchViewModel(movieUseCase: IMovieUseCase) : ViewModel() {
    private val _queryParam = MutableLiveData<String>()
    val searchResult = Transformations.switchMap(_queryParam) {
        movieUseCase.searchMovies(it.lowercase()).asLiveData()
    }

    fun setQuery(searchQuery: String) {
        _queryParam.value = searchQuery
    }
}