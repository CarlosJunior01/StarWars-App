package com.carlosjunior.testing.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.carlosjunior.core.domain.model.Persons

class PagingSourcePersonsFactory {

    fun create(persons: List<Persons>) = object : PagingSource<Int, Persons>() {
        override fun getRefreshKey(state: PagingState<Int, Persons>) = 1

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Persons> {
            return LoadResult.Page(
                data = persons,
                prevKey = null,
                nextKey = 20
            )
        }
    }
}