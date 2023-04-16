package com.carlosjunior.starwarsapp.framework

import com.carlosjunior.core.data.repository.PersonsSearchRepository
import com.carlosjunior.core.domain.model.Persons
import com.carlosjunior.starwarsapp.framework.network.StarWarsApi
import com.carlosjunior.starwarsapp.framework.network.response.toPersonsModel
import javax.inject.Inject

class PersonsSearchRepositoryImpl @Inject constructor(
    private val apiService: StarWarsApi
) : PersonsSearchRepository {

    override suspend fun getSearchPersons(search: String): List<Persons> {
        return try {
            apiService.getSearchPersons(search = search).results.map { it.toPersonsModel() }
        } catch (ex: Exception) {
            throw Exception()
        }
    }
}