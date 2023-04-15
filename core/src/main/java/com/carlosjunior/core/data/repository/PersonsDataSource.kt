package com.carlosjunior.core.data.repository

interface PersonsDataSource<T> {
    suspend fun fetchApiPersons(positionPage: Int): T
}