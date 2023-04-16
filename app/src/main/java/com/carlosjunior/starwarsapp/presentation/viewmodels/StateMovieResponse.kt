package com.carlosjunior.starwarsapp.presentation.viewmodels

import androidx.paging.PagingData
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject

sealed class StateMovieResponse {
    data class StateMoviesSuccess(val it: PagingData<MoviesViewObject>) : StateMovieResponse()
    object StateMoviesLoading: StateMovieResponse()
}
