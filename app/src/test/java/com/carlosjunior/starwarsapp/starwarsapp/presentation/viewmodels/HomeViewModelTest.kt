package com.carlosjunior.starwarsapp.starwarsapp.presentation.viewmodels

import androidx.paging.PagingData
import com.carlosjunior.core.usecase.GetMoviesUseCase
import com.carlosjunior.core.usecase.GetPersonsUseCase
import com.carlosjunior.core.usecase.GetSearchMoviesUseCase
import com.carlosjunior.core.usecase.GetSearchPersonsUseCase
import com.carlosjunior.starwarsapp.presentation.viewmodels.HomeViewModel
import com.carlosjunior.testing.MainCoroutineRule
import com.carlosjunior.testing.model.MoviesFactory
import com.carlosjunior.testing.model.PersonsFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getPersonsUseCase: GetPersonsUseCase

    @Mock
    lateinit var getSearchPersonsUseCase: GetSearchPersonsUseCase

    @Mock
    lateinit var getSearchMoviesUseCase: GetSearchMoviesUseCase

    @Mock
    lateinit var getMoviesUseCase: GetMoviesUseCase

    private lateinit var homeViewModel: HomeViewModel
    private val personsFactory = PersonsFactory()
    private val moviesFactory = MoviesFactory()

    private val personsFake = PagingData.from(
        listOf(
            personsFactory.create(PersonsFactory.PersonsClass.Luke),
            personsFactory.create(PersonsFactory.PersonsClass.Darth)
        )
    )

    private val moviesFake = PagingData.from(
        listOf(
            moviesFactory.create(MoviesFactory.MoviesClass.NewHope),
            moviesFactory.create(MoviesFactory.MoviesClass.TheEmpire)
        )
    )

    private val searchPersonsFake =
        listOf(
            personsFactory.create(PersonsFactory.PersonsClass.Luke),
            personsFactory.create(PersonsFactory.PersonsClass.Darth)
        )

    private val searchMoviesFake =
        listOf(
            moviesFactory.create(MoviesFactory.MoviesClass.NewHope),
            moviesFactory.create(MoviesFactory.MoviesClass.TheEmpire)
        )

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(
            getPersonsUseCase,
            getSearchPersonsUseCase,
            getSearchMoviesUseCase,
            getMoviesUseCase
        )
    }

    @Test
    fun `should validate the paging data object values when calling personsPagingData`() =
        runTest {
            whenever(
                getPersonsUseCase.invoke(any())
            ).thenReturn(
                flowOf(
                    personsFake
                )
            )
            val result = homeViewModel.personsPagingData("")

            assertNotNull(result)
        }

    @Test
    fun `should validate the paging data object values when calling moviesPagingData`() =
        runTest {
            whenever(
                getMoviesUseCase.invoke(any())
            ).thenReturn(
                flowOf(
                    moviesFake
                )
            )
            val result = homeViewModel.moviesPagingData("")

            assertNotNull(result)
        }

    @Test
    fun `should validate the paging data object values when calling personsSearch`() =
        runTest {
            whenever(
                getSearchPersonsUseCase.invoke(any())
            ).thenReturn(
                Result.success(
                    searchPersonsFake
                )
            ).runCatching { homeViewModel.personsSearch("") }
            assertNotNull(homeViewModel.screenState.value)
        }

    @Test
    fun `should validate the paging data object values when calling moviesSearch`() =
        runTest {
            whenever(
                getSearchMoviesUseCase.invoke(any())
            ).thenReturn(
                Result.success(
                    searchMoviesFake
                )
            ).runCatching { homeViewModel.moviesSearch("") }
            assertNotNull(homeViewModel.screenState.value)
        }

    @Test(expected = Throwable::class)
    fun `should throw an exception when the calling to Persons returns exception`() =
        runTest {
            whenever(
                getPersonsUseCase.invoke(any())
            ).runCatching { homeViewModel.personsPagingData("") }
            assertNull(homeViewModel.screenState.value)
        }

    @Test(expected = Throwable::class)
    fun `should throw an exception when the calling to Movies returns exception`() =
        runTest {
            whenever(
                getMoviesUseCase.invoke(any())
            ).runCatching { homeViewModel.moviesPagingData("") }
            assertNull(homeViewModel.screenMovieState.value)
        }

    @Test(expected = Throwable::class)
    fun `should throw an exception when the calling to SearchPersons returns exception`() =
        runTest {
            whenever(
                getSearchPersonsUseCase.invoke(any())
            ).thenReturn(
                Result.failure(
                    Throwable()
                )
            ).runCatching { homeViewModel.personsSearch("") }
            assertNull(homeViewModel.screenState.value)
        }

    @Test(expected = Throwable::class)
    fun `should throw an exception when the calling to SearchMovies returns exception`() =
        runTest {
            whenever(
                getSearchMoviesUseCase.invoke(any())
            ).thenReturn(
                Result.failure(
                    Throwable()
                )
            ).runCatching { homeViewModel.moviesSearch("") }
            assertNull(homeViewModel.screenState.value)
        }
}
