package com.carlosjunior.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.carlosjunior.core.data.repository.MoviesRepository
import com.carlosjunior.core.domain.model.Movies
import com.carlosjunior.core.usecase.GetMoviesUseCase.GetMoviesParams
import com.carlosjunior.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMoviesUseCase {
    operator fun invoke(params: GetMoviesParams): Flow<PagingData<Movies>>
    data class GetMoviesParams(val query: String, val pagingConfig: PagingConfig)
}

class GetMoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : PagingUseCase<GetMoviesParams, Movies>(), GetMoviesUseCase {

    override fun createFlowObservable(params: GetMoviesParams): Flow<PagingData<Movies>> {
        val pagingSource = moviesRepository.getMovies(params.query)
        return Pager(config = params.pagingConfig) {pagingSource}.flow
    }
}