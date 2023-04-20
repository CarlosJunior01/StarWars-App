package com.carlosjunior.testing.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.carlosjunior.core.domain.model.Movies

class PagingSourceMoviesFactory {

    fun create(movies: List<Movies>) = object : PagingSource<Int, Movies>() {
        override fun getRefreshKey(state: PagingState<Int, Movies>) = 1

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {
            return LoadResult.Page(
                data = movies,
                prevKey = null,
                nextKey = null
            )
        }
    }
}