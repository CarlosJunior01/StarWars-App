package com.carlosjunior.core.usecase

import androidx.paging.PagingConfig
import com.carlosjunior.core.data.repository.MoviesRepository
import com.carlosjunior.testing.MainCoroutineRule
import com.carlosjunior.testing.model.MoviesFactory
import com.carlosjunior.testing.pagingsource.PagingSourceMoviesFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class GetMoviesUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: MoviesRepository

    private lateinit var getMoviesUseCase: GetMoviesUseCaseImpl

    private val moviesRepository = MoviesFactory().create(MoviesFactory.MoviesClass.NewHope)

    private val pagingSourceFactory = PagingSourceMoviesFactory().create(listOf(moviesRepository))

    @Before
    fun setUp() {
        getMoviesUseCase = GetMoviesUseCaseImpl(repository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {
            whenever(repository.getMovies(""))
                .thenReturn(pagingSourceFactory)

            val result = getMoviesUseCase
                .invoke(GetMoviesUseCase.GetMoviesParams("", PagingConfig(1)))

            Assert.assertNotNull(result.first())
        }
}
