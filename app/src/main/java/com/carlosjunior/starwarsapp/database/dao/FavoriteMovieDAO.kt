package com.carlosjunior.starwarsapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carlosjunior.starwarsapp.database.model.FavoriteMovie

@Dao
interface FavoriteMovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteMovie: FavoriteMovie)

    @Query("SELECT*FROM FavoriteMovie")
    suspend fun searchAll(): List<FavoriteMovie>

    @Delete
    suspend fun deleteFavoritePerson(favoriteMovie: FavoriteMovie)

}