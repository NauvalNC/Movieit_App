package com.nauval.movieit.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.nauval.movieit.core.data.Resource
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var useCase: IMovieUseCase
    private lateinit var viewModel: HomeViewModel
    private val dummyData = DataDummy.generateDummyMovies()

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
        val expectedList = MutableLiveData<Resource<List<Movie>>>()
        expectedList.value = Resource.Success(dummyData)

        `when`(useCase.getAllMovies()).thenReturn(expectedList.asFlow())
        viewModel = HomeViewModel(useCase)

        val actualList = viewModel.lastMovies.getOrAwaitValue()
        verify(useCase).getAllMovies()

        Assert.assertNotNull(actualList)
        Assert.assertNotNull(actualList.data)
        Assert.assertTrue(actualList is Resource.Success)
        Assert.assertEquals((actualList as Resource.Success).data?.size, dummyData.size)
    }

    @Test
    fun `When network error, should return error`() = runTest {
        val expectedList = MutableLiveData<Resource<List<Movie>>>()
        expectedList.value = Resource.Error("Network error")

        `when`(useCase.getAllMovies()).thenReturn(expectedList.asFlow())
        viewModel = HomeViewModel(useCase)

        val actualList = viewModel.lastMovies.getOrAwaitValue()
        verify(useCase).getAllMovies()

        Assert.assertNotNull(actualList)
        Assert.assertTrue(actualList is Resource.Error)
    }
}