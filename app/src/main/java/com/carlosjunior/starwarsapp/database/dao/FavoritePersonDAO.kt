package com.carlosjunior.starwarsapp.database.dao

import androidx.room.*
import com.carlosjunior.starwarsapp.database.model.FavoritePerson

@Dao
interface FavoritePersonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoritePerson: FavoritePerson)

    @Query("SELECT*FROM FavoritePerson")
    suspend fun searchAll(): List<FavoritePerson>

    @Delete
    suspend fun deleteFavoritePerson(favoritePerson: FavoritePerson)

}