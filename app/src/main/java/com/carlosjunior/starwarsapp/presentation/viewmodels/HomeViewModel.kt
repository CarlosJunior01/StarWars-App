package com.carlosjunior.starwarsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.carlosjunior.core.usecase.GetMoviesUseCase
import com.carlosjunior.core.usecase.GetPersonsUseCase
import com.carlosjunior.core.usecase.GetSearchMoviesUseCase
import com.carlosjunior.core.usecase.GetSearchPersonsUseCase
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject
import com.carlosjunior.starwarsapp.presentation.viewmodels.StateMovieResponse.StateMoviesLoading
import com.carlosjunior.starwarsapp.presentation.viewmodels.StateMovieResponse.StateMoviesSuccess
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StatePersonsError
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StatePersonsLoading
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StatePersonsSuccess
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StateSearchMoviesSuccess
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StateSearchPersonsSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPersonsUseCase: GetPersonsUseCase,
    private val getSearchPersonsUseCase: GetSearchPersonsUseCase,
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<StatePersonsResponse>(StatePersonsLoading)
    val screenState: StateFlow<StatePersonsResponse> = _screenState

    private val _screenMovieState = MutableStateFlow<StateMovieResponse>(StateMoviesLoading)
    val screenMovieState: StateFlow<StateMovieResponse> = _screenMovieState

    init {
        personsPagingData(query = EMPTY)
        moviesPagingData(query = EMPTY)
    }

    fun personsPagingData(query: String) {
        viewModelScope.launch {
            getPersonsUseCase(
                GetPersonsUseCase.GetPersonsParams(query, getPageConfig())
            ).cachedIn(viewModelScope).collectLatest {
                val personViewObject = it.map { person -> PersonsViewObject(person) }
                _screenState.value = StatePersonsSuccess(personViewObject)
            }
        }
    }

    fun personsSearch(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getSearchPersonsUseCase(search).onSuccess { persons ->
                val personViewObject = persons.map { person -> PersonsViewObject(person) }
                _screenState.value = StateSearchPersonsSuccess(personsVO = personViewObject)
            }.onFailure {
                _screenState.value = StatePersonsError
            }
        }
    }

    fun moviesSearch(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getSearchMoviesUseCase(search).onSuccess { movies ->
                val movieViewObject = movies.map { movie -> MoviesViewObject(movie) }
                _screenState.value = StateSearchMoviesSuccess(moviesVO = movieViewObject)
            }.onFailure {
                _screenState.value = StatePersonsError
            }
        }
    }

    fun moviesPagingData(query: String) {
        viewModelScope.launch {
            getMoviesUseCase(
                GetMoviesUseCase.GetMoviesParams(query, getPageConfig())
            ).cachedIn(viewModelScope).collectLatest {
                val moviesViewObject = it.map { movie -> MoviesViewObject(movie) }
                _screenMovieState.value = StateMoviesSuccess(moviesViewObject)
            }
        }
    }

    private fun getPageConfig() = PagingConfig(pageSize = SIZE_10)

    companion object {
        const val EMPTY = ""
        private const val SIZE_10 = 10
    }
}