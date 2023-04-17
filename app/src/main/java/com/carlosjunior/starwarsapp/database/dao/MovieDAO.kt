package com.carlosjunior.starwarsapp.database.dao

import androidx.room.*
import com.carlosjunior.starwarsapp.database.model.Movie

@Dao
interface MovieDAO {

    @Insert
    suspend fun insert(movie: Movie)

    @Query("SELECT*FROM Movie")
    suspend fun searchAll(): List<Movie>

}