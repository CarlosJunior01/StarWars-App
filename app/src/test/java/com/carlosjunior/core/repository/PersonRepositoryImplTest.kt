package com.carlosjunior.core.repository

import com.carlosjunior.starwarsapp.framework.PersonsRepositoryImpl
import com.carlosjunior.starwarsapp.framework.network.StarWarsApi
import com.carlosjunior.starwarsapp.framework.remote.RetrofitPersonsDataSource
import com.carlosjunior.testing.MainCoroutineRule
import com.carlosjunior.testing.model.PersonsFactory
import com.carlosjunior.testing.pagingsource.PagingSourcePersonsFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PersonRepositoryImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repositoryImpl: PersonsRepositoryImpl

    @Mock
    lateinit var apiService: StarWarsApi

    private lateinit var dataSource: RetrofitPersonsDataSource

    private val personsRepository = PersonsFactory().create(PersonsFactory.PersonsClass.Luke)

    private val pagingSourceFactory = PagingSourcePersonsFactory().create(listOf(personsRepository))

    @Before
    fun setUp() {
        dataSource = RetrofitPersonsDataSource(apiService)
        repositoryImpl = PersonsRepositoryImpl(dataSource)
    }

    @Test
    fun `getPersons return success with a list of persons`() =
        runTest {
            coroutineContext.runCatching {
                whenever(
                    repositoryImpl.getPersons("")
                ).thenReturn(
                    pagingSourceFactory
                )
                val result = apiService.getPersons(1)
                Assert.assertNotNull(result)
            }
        }
}
