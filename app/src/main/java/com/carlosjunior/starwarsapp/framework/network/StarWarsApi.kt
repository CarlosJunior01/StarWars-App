package com.carlosjunior.starwarsapp.framework.network

import com.carlosjunior.starwarsapp.framework.network.response.MoviesResponseDTO
import com.carlosjunior.starwarsapp.framework.network.response.PersonsResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApi {

    @GET("people")
    suspend fun getPersons(
        @Query("page") page: Int
    ) : PersonsResponseDTO

    @GET("people")
    suspend fun getSearchPersons(
        @Query("search") search: String
    ) : PersonsResponseDTO

    @GET("films")
    suspend fun getSearchMovies(
        @Query("search") search: String
    ) : MoviesResponseDTO

    @GET("films")
    suspend fun getMovies(
        @Query("page") page: Int
    ) : MoviesResponseDTO
}
