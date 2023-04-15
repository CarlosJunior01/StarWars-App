package com.carlosjunior.starwarsapp.framework.di

import com.carlosjunior.core.usecase.GetMoviesUseCase
import com.carlosjunior.core.usecase.GetMoviesUseCaseImpl
import com.carlosjunior.core.usecase.GetPersonsUseCase
import com.carlosjunior.core.usecase.GetPersonsUseCaseImpl
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
    fun bindMoviesUseCase(useCase: GetMoviesUseCaseImpl): GetMoviesUseCase
}