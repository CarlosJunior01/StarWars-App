package com.carlosjunior.starwarsapp.framework

import androidx.paging.PagingSource
import com.carlosjunior.core.data.repository.MoviesDataSource
import com.carlosjunior.core.data.repository.MoviesRepository
import com.carlosjunior.core.domain.model.Movies
import com.carlosjunior.starwarsapp.framework.network.response.MoviesResponseDTO
import com.carlosjunior.starwarsapp.framework.paging.MoviesPagingSource
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesDataSource: MoviesDataSource<MoviesResponseDTO>
): MoviesRepository {

    override fun getMovies(query: String): PagingSource<Int, Movies> {
        return MoviesPagingSource(moviesDataSource)
    }
}