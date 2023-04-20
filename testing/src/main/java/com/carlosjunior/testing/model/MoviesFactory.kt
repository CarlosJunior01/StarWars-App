package com.carlosjunior.testing.model

import com.carlosjunior.core.domain.model.Movies

class MoviesFactory {

    fun create(movies: MoviesClass) = when (movies) {
        MoviesClass.NewHope -> Movies(
            title = "A New Hope",
            episodeId = 4,
            openingCrawl = "",
            director = "George Lucas",
            producer = "Gary Kurtz, Rick McCallum",
            releaseDate = "1977-05-25",
            url = ""
        )
        MoviesClass.TheEmpire -> Movies(
            title = "The Empire Strikes Back",
            episodeId = 5,
            openingCrawl = "",
            director = "Irvin Kershner",
            producer = "Gary Kurtz, Rick McCallum",
            releaseDate = "1980-05-17",
            url = ""
        )
    }

    sealed class MoviesClass {
        object NewHope: MoviesClass()
        object TheEmpire: MoviesClass()
    }
}