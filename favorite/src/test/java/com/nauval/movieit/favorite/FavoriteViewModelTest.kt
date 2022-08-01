package com.nauval.movieit.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.nauval.movieit.core.domain.model.Movie
import com.nauval.movieit.core.domain.usecase.IMovieUseCase
import com.nauval.movieit.core.util.DataDummy
import com.nauval.movieit.core.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var useCase: IMovieUseCase
    private lateinit var viewModel: FavoriteViewModel
    private val dummyData = DataDummy.generateDummyMovies(onlyFavorites = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When get all movies success, should not null and return success`() = runTest {
        val expectedList = MutableLiveData<List<Movie>>()
        expectedList.value = dummyData

        Mockito.`when`(useCase.getFavoriteMovie()).thenReturn(expectedList.asFlow())
        viewModel = FavoriteViewModel(useCase)

        val actualList = viewModel.favoriteMovies.getOrAwaitValue()
        Mockito.verify(useCase).getFavoriteMovie()

        Assert.assertNotNull(actualList)
        Assert.assertEquals(actualList.size, dummyData.size)
    }

    @Test
    fun `When no favorites, should return 0 items`() = runTest {
        val expectedList = MutableLiveData<List<Movie>>()
        expectedList.value = ArrayList()

        Mockito.`when`(useCase.getFavoriteMovie()).thenReturn(expectedList.asFlow())
        viewModel = FavoriteViewModel(useCase)

        val actualList = viewModel.favoriteMovies.getOrAwaitValue()
        Mockito.verify(useCase).getFavoriteMovie()

        Assert.assertNotNull(actualList)
        Assert.assertEquals(actualList.size, 0)
    }
}