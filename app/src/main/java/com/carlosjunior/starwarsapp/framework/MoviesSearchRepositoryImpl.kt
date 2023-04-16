package com.carlosjunior.starwarsapp.framework

import com.carlosjunior.core.data.repository.MoviesSearchRepository
import com.carlosjunior.core.domain.model.Movies
import com.carlosjunior.starwarsapp.framework.network.StarWarsApi
import com.carlosjunior.starwarsapp.framework.network.response.toMoviesModel
import javax.inject.Inject

class MoviesSearchRepositoryImpl @Inject constructor(
    private val apiService: StarWarsApi
) : MoviesSearchRepository {

    override suspend fun getSearchMovies(search: String): List<Movies> {
        return try {
            apiService.getSearchMovies(search = search).results.map { it.toMoviesModel() }
        } catch (ex: Exception) {
            throw Exception()
        }
    }
}