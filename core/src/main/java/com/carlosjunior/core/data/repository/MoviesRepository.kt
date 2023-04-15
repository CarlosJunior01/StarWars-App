package com.carlosjunior.core.data.repository

import androidx.paging.PagingSource
import com.carlosjunior.core.domain.model.Movies

interface MoviesRepository {
    fun getMovies(query: String): PagingSource<Int, Movies>
}