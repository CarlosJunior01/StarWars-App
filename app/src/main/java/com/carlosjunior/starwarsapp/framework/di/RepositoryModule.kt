package com.carlosjunior.starwarsapp.framework.di

import com.carlosjunior.core.data.repository.MoviesDataSource
import com.carlosjunior.core.data.repository.MoviesRepository
import com.carlosjunior.core.data.repository.PersonsRepository
import com.carlosjunior.core.data.repository.PersonsDataSource
import com.carlosjunior.starwarsapp.framework.MoviesRepositoryImpl
import com.carlosjunior.starwarsapp.framework.PersonsRepositoryImpl
import com.carlosjunior.starwarsapp.framework.network.response.MoviesResponseDTO
import com.carlosjunior.starwarsapp.framework.network.response.PersonsResponseDTO
import com.carlosjunior.starwarsapp.framework.remote.RetrofitMoviesDataSource
import com.carlosjunior.starwarsapp.framework.remote.RetrofitPersonsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindPersons(repository: PersonsRepositoryImpl): PersonsRepository

    @Binds
    fun bindMovies(repository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    fun bindPersonsDataSource(dataSource: RetrofitPersonsDataSource): PersonsDataSource<PersonsResponseDTO>

    @Binds
    fun bindMoviesDataSource(dataSource: RetrofitMoviesDataSource): MoviesDataSource<MoviesResponseDTO>
}