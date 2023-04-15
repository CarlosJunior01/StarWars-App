package com.carlosjunior.starwarsapp.framework.network.response

import com.carlosjunior.core.domain.model.Movies
import com.google.gson.annotations.SerializedName

data class MoviesResponseDTO(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<MovieResults>
)

data class MovieResults(
    @SerializedName("title") val title: String?,
    @SerializedName("episode_id") val episodeId: Int?,
    @SerializedName("opening_crawl") val openingCrawl: String?,
    @SerializedName("director") val director: String?,
    @SerializedName("producer") val producer: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("url") val url: String?
)

fun MovieResults.toMoviesModel(): Movies {
    return Movies(
        title = this.title,
        episodeId = this.episodeId,
        openingCrawl = this.openingCrawl,
        director = this.director,
        producer = this.producer,
        releaseDate = this.releaseDate,
        url = this.url
    )
}
