package com.carlosjunior.starwarsapp.framework.remote

import com.carlosjunior.core.data.repository.MoviesDataSource
import com.carlosjunior.starwarsapp.framework.network.StarWarsApi
import com.carlosjunior.starwarsapp.framework.network.response.MoviesResponseDTO
import javax.inject.Inject

class RetrofitMoviesDataSource @Inject constructor(
    private val starWarsApi: StarWarsApi
): MoviesDataSource<MoviesResponseDTO> {

    override suspend fun fetchApiMovies(positionPage: Int): MoviesResponseDTO {
        return starWarsApi.getMovies(page = positionPage )
    }
}