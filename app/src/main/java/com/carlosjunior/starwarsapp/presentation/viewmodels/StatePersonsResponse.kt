package com.carlosjunior.starwarsapp.presentation.viewmodels

import androidx.paging.PagingData
import com.carlosjunior.core.domain.model.Persons

sealed class StatePersonsResponse {
    data class StatePersonsSuccess(val it: PagingData<Persons>) : StatePersonsResponse()
    object StatePersonsLoading: StatePersonsResponse()
}
