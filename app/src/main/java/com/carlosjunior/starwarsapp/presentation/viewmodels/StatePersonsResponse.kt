package com.carlosjunior.starwarsapp.presentation.viewmodels

import androidx.paging.PagingData
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject

sealed class StatePersonsResponse {
    data class StatePersonsSuccess(val it: PagingData<PersonsViewObject>): StatePersonsResponse()
    data class StateSearchPersonsSuccess(val personsVO: List<PersonsViewObject>): StatePersonsResponse()
    data class StateSearchMoviesSuccess(val moviesVO: List<MoviesViewObject>): StatePersonsResponse()
    object StatePersonsLoading: StatePersonsResponse()
    object StatePersonsError: StatePersonsResponse()
}
