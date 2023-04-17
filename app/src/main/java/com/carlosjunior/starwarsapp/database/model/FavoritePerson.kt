package com.carlosjunior.starwarsapp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritePerson(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val height: String,
    val hairColor: String,
    val skinColor: String,
    val birthYear: String,
    val gender: String,
    val url: String
)