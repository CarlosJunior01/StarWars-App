package com.carlosjunior.starwarsapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.carlosjunior.core.data.repository.PersonsDataSource
import com.carlosjunior.core.domain.model.Persons
import com.carlosjunior.starwarsapp.framework.network.response.PersonsResponseDTO
import com.carlosjunior.starwarsapp.framework.network.response.toPersonsModel
import java.lang.Exception

class PersonsPagingSource(
    private val personsDataSource: PersonsDataSource<PersonsResponseDTO>
) : PagingSource<Int, Persons>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Persons> {
        return try {
            val positionPage = params.key ?: INITIAL_PAGE_INDEX
            val response = personsDataSource.fetchApiPersons(positionPage = positionPage)

            LoadResult.Page(
                data = response.results.map { it.toPersonsModel() },
                prevKey = null,
                nextKey = if (response.results.isEmpty()) null else positionPage + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Persons>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val INITIAL_PAGE_INDEX: Int = 1
        private const val LIMIT = 2
    }
}