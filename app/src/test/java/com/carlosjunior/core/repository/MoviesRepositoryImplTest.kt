package com.carlosjunior.core.repository

import com.carlosjunior.starwarsapp.framework.MoviesRepositoryImpl
import com.carlosjunior.starwarsapp.framework.network.StarWarsApi
import com.carlosjunior.starwarsapp.framework.remote.RetrofitMoviesDataSource
import com.carlosjunior.testing.MainCoroutineRule
import com.carlosjunior.testing.model.MoviesFactory
import com.carlosjunior.testing.pagingsource.PagingSourceMoviesFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repositoryImpl: MoviesRepositoryImpl

    @Mock
    lateinit var apiService: StarWarsApi

    private lateinit var dataSource: RetrofitMoviesDataSource

    private val moviesRepository = MoviesFactory().create(MoviesFactory.MoviesClass.NewHope)

    private val pagingSourceFactory = PagingSourceMoviesFactory().create(listOf(moviesRepository))

    @Before
    fun setUp() {
        dataSource = RetrofitMoviesDataSource(apiService)
        repositoryImpl = MoviesRepositoryImpl(dataSource)
    }

    @Test
    fun `getMovies return success with a list of movies`() =
        runTest {
            coroutineContext.runCatching {
                whenever(
                    repositoryImpl.getMovies("")
                ).thenReturn(
                    pagingSourceFactory
                )
                val result = apiService.getMovies(1)
                Assert.assertNotNull(result)
            }
        }
}
