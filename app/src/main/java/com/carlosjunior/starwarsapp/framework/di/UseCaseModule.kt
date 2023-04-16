package com.carlosjunior.starwarsapp.framework.di

import com.carlosjunior.core.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindPersonsUseCase(useCase: GetPersonsUseCaseImpl): GetPersonsUseCase

    @Binds
    fun bindSearchPersonsUseCase(useCase: GetSearchPersonUseCaseImpl): GetSearchPersonsUseCase

    @Binds
    fun bindSearchMoviesUseCase(useCase: GetSearchMoviesUseCaseImpl): GetSearchMoviesUseCase

    @Binds
    fun bindMoviesUseCase(useCase: GetMoviesUseCaseImpl): GetMoviesUseCase
}