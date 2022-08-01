package com.nauval.movieit.detail

import com.nauval.movieit.core.domain.usecase.IMovieUseCase
import com.nauval.movieit.core.util.DataDummy
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @Mock
    private lateinit var useCase: IMovieUseCase
    private lateinit var viewModel: DetailViewModel
    private val dummyMovie = DataDummy.dummyMovie
    private val dummyState = true

    @Before
    fun setup() {
        viewModel = DetailViewModel(useCase)
    }

    @Test
    fun `When set favorite, use case should be called`() {
        doNothing().`when`(useCase).setFavoriteMovie(dummyMovie, dummyState)
        viewModel.setFavoriteMovie(dummyMovie, dummyState)
        verify(useCase).setFavoriteMovie(dummyMovie, dummyState)
    }
}