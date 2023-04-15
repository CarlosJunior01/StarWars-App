package com.carlosjunior.core.data.repository

interface MoviesDataSource<T> {
    suspend fun fetchApiMovies(positionPage: Int): T
}