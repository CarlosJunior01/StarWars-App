package com.carlosjunior.core.usecase

import com.carlosjunior.core.data.repository.MoviesSearchRepository
import com.carlosjunior.core.domain.model.Movies
import javax.inject.Inject

interface GetSearchMoviesUseCase {
    suspend operator fun invoke(search: String): Result<List<Movies>>
}

class GetSearchMoviesUseCaseImpl @Inject constructor(
    private val repository: MoviesSearchRepository
) : GetSearchMoviesUseCase {

    override suspend operator fun invoke(search: String): Result<List<Movies>> {
        return try {
            Result.success(repository.getSearchMovies(search))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}

