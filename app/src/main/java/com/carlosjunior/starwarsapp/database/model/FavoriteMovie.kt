package com.carlosjunior.starwarsapp.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity (indices = [Index(value = ["title"], unique = true)])
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val episodeId: Int,
    val openingCrawl: String,
    val director: String,
    val producer: String,
    val releaseDate: String,
    val url: String
)