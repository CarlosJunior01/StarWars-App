package com.carlosjunior.core.data.repository

import com.carlosjunior.core.domain.model.Movies

interface MoviesSearchRepository {
    suspend fun getSearchMovies(search: String): List<Movies>
}