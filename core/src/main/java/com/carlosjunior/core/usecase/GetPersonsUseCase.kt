package com.carlosjunior.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.carlosjunior.core.data.repository.PersonsRepository
import com.carlosjunior.core.domain.model.Persons
import com.carlosjunior.core.usecase.GetPersonsUseCase.GetPersonsParams
import com.carlosjunior.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPersonsUseCase {
    operator fun invoke(params: GetPersonsParams): Flow<PagingData<Persons>>
    data class GetPersonsParams(val query: String, val pagingConfig: PagingConfig)
}

class GetPersonsUseCaseImpl @Inject constructor(
    private val personsRepository: PersonsRepository
) : PagingUseCase<GetPersonsParams, Persons>(), GetPersonsUseCase {

    override fun createFlowObservable(params: GetPersonsParams): Flow<PagingData<Persons>> {
        val pagingSource = personsRepository.getPersons(params.query)
        return Pager(config = params.pagingConfig) {pagingSource}.flow
    }
}