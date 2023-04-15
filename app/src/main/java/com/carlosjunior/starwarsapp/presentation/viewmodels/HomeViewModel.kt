package com.carlosjunior.starwarsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.carlosjunior.core.usecase.GetMoviesUseCase
import com.carlosjunior.core.usecase.GetPersonsUseCase
import com.carlosjunior.starwarsapp.presentation.viewmodels.StateMovieResponse.StateMoviesLoading
import com.carlosjunior.starwarsapp.presentation.viewmodels.StateMovieResponse.StateMoviesSuccess
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StatePersonsLoading
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StatePersonsSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPersonsUseCase: GetPersonsUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
): ViewModel() {

    private val _screenState = MutableStateFlow<StatePersonsResponse>(StatePersonsLoading)
    val screenState: StateFlow<StatePersonsResponse> = _screenState

    private val _screenMovieState = MutableStateFlow<StateMovieResponse>(StateMoviesLoading)
    val screenMovieState: StateFlow<StateMovieResponse> = _screenMovieState

    init {
        personsPagingData(query = EMPTY)
        moviesPagingData(query = EMPTY)
    }

     private fun personsPagingData(query: String) {
         viewModelScope.launch {
             getPersonsUseCase(
                 GetPersonsUseCase.GetPersonsParams(query, getPageConfig())
             ).cachedIn(viewModelScope).collectLatest {
                 _screenState.value = StatePersonsSuccess(it)
             }
         }
    }

    private fun moviesPagingData(query: String) {
        viewModelScope.launch {
            getMoviesUseCase(
                GetMoviesUseCase.GetMoviesParams(query, getPageConfig())
            ).cachedIn(viewModelScope).collectLatest {
                _screenMovieState.value = StateMoviesSuccess(it)
            }
        }
    }

    private fun getPageConfig() = PagingConfig(pageSize = SIZE_10)

    companion object {
        private const val EMPTY = ""
        private const val SIZE_10 = 10
    }
}