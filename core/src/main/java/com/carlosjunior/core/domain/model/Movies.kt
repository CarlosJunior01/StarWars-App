package com.carlosjunior.core.domain.model

data class Movies(
    val title: String?,
    val episodeId: Int?,
    val openingCrawl: String?,
    val director: String?,
    val producer: String?,
    val releaseDate: String?,
    val url: String?
): java.io.Serializable