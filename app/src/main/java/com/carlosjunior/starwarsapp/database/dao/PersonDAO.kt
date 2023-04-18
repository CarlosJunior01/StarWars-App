package com.carlosjunior.starwarsapp.database.dao

import androidx.room.*
import com.carlosjunior.starwarsapp.database.model.Person

@Dao
interface PersonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: Person)

    @Query("SELECT*FROM Person")
    suspend fun searchAll(): List<Person>

}