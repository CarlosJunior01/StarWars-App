package com.carlosjunior.core.data.repository

import com.carlosjunior.core.domain.model.Persons

interface PersonsSearchRepository {
    suspend fun getSearchPersons(search: String): List<Persons>
}