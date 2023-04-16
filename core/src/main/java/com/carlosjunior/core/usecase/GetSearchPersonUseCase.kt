package com.carlosjunior.core.usecase

import com.carlosjunior.core.data.repository.PersonsSearchRepository
import com.carlosjunior.core.domain.model.Persons
import javax.inject.Inject

interface GetSearchPersonsUseCase {
    suspend operator fun invoke(search: String): Result<List<Persons>>
}

class GetSearchPersonUseCaseImpl @Inject constructor(
    private val repository: PersonsSearchRepository
) : GetSearchPersonsUseCase {

    override suspend operator fun invoke(search: String): Result<List<Persons>> {
        return try {
            Result.success(repository.getSearchPersons(search))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}

