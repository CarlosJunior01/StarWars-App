package com.carlosjunior.starwarsapp.presentation.model

import android.os.Parcelable
import com.carlosjunior.core.domain.model.Movies
import com.carlosjunior.starwarsapp.database.model.FavoriteMovie
import com.carlosjunior.starwarsapp.database.model.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesViewObject(
    val title: String?,
    val episodeId: Int?,
    val openingCrawl: String?,
    val director: String?,
    val producer: String?,
    val releaseDate: String?,
    val url: String?
) : Parcelable {

    constructor(movies: Movies) : this(
        title = movies.title,
        episodeId = movies.episodeId,
        openingCrawl = movies.openingCrawl,
        director = movies.director,
        producer = movies.producer,
        releaseDate = movies.releaseDate,
        url = movies.url
    )

    constructor(movies: Movie) : this(
        title = movies.title,
        episodeId = movies.episodeId,
        openingCrawl = movies.openingCrawl,
        director = movies.director,
        producer = movies.producer,
        releaseDate = movies.releaseDate,
        url = movies.url
    )

    constructor(movies: FavoriteMovie) : this(
        title = movies.title,
        episodeId = movies.episodeId,
        openingCrawl = movies.openingCrawl,
        director = movies.director,
        producer = movies.producer,
        releaseDate = movies.releaseDate,
        url = movies.url
    )
}

