package com.carlosjunior.starwarsapp.presentation.viewmodels

import androidx.paging.PagingData
import com.carlosjunior.core.domain.model.Movies

sealed class StateMovieResponse {
    data class StateMoviesSuccess(val it: PagingData<Movies>) : StateMovieResponse()
    object StateMoviesLoading: StateMovieResponse()
}
