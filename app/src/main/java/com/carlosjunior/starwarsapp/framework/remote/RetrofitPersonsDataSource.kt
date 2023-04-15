package com.carlosjunior.starwarsapp.framework.remote

import com.carlosjunior.core.data.repository.PersonsDataSource
import com.carlosjunior.starwarsapp.framework.network.StarWarsApi
import com.carlosjunior.starwarsapp.framework.network.response.PersonsResponseDTO
import javax.inject.Inject

class RetrofitPersonsDataSource @Inject constructor(
    private val starWarsApi: StarWarsApi
): PersonsDataSource<PersonsResponseDTO> {

    override suspend fun fetchApiPersons(positionPage: Int): PersonsResponseDTO {
        return starWarsApi.getPersons(page = positionPage )
    }
}