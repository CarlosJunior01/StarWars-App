package com.carlosjunior.starwarsapp.framework

import androidx.paging.PagingSource
import com.carlosjunior.core.data.repository.PersonsRepository
import com.carlosjunior.core.data.repository.PersonsDataSource
import com.carlosjunior.core.domain.model.Persons
import com.carlosjunior.starwarsapp.framework.network.response.PersonsResponseDTO
import com.carlosjunior.starwarsapp.framework.paging.PersonsPagingSource
import javax.inject.Inject

class PersonsRepositoryImpl @Inject constructor(
    private val personsDataSource: PersonsDataSource<PersonsResponseDTO>
): PersonsRepository {

    override fun getPersons(query: String): PagingSource<Int, Persons> {
        return PersonsPagingSource(personsDataSource)
    }
}