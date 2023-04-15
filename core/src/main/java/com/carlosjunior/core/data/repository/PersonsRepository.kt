package com.carlosjunior.core.data.repository

import androidx.paging.PagingSource
import com.carlosjunior.core.domain.model.Persons

interface PersonsRepository {
    fun getPersons(query: String): PagingSource<Int, Persons>
}