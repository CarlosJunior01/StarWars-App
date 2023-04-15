package com.carlosjunior.starwarsapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.carlosjunior.core.data.repository.MoviesDataSource
import com.carlosjunior.core.domain.model.Movies
import com.carlosjunior.starwarsapp.framework.network.response.MoviesResponseDTO
import com.carlosjunior.starwarsapp.framework.network.response.toMoviesModel
import java.lang.Exception

class MoviesPagingSource(
    private val moviesDataSource: MoviesDataSource<MoviesResponseDTO>
) : PagingSource<Int, Movies>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {
        return try {
            val positionPage = params.key ?: INITIAL_PAGE_INDEX
            val response = moviesDataSource.fetchApiMovies(positionPage = positionPage)

            LoadResult.Page(
                data = response.results.map { it.toMoviesModel() },
                prevKey = null,
                nextKey = null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {
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