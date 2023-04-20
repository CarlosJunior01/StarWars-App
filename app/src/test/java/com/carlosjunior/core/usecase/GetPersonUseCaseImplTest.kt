package com.carlosjunior.core.usecase

import androidx.paging.PagingConfig
import com.carlosjunior.core.data.repository.PersonsRepository
import com.carlosjunior.testing.MainCoroutineRule
import com.carlosjunior.testing.model.PersonsFactory
import com.carlosjunior.testing.pagingsource.PagingSourcePersonsFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class GetPersonUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: PersonsRepository

    private lateinit var getPersonsUseCase: GetPersonsUseCase

    private val  personsRepository = PersonsFactory().create(PersonsFactory.PersonsClass.Luke)

    private val pagingSourceFactory = PagingSourcePersonsFactory().create(listOf(personsRepository))

    @Before
    fun setUp() {
        getPersonsUseCase = GetPersonsUseCaseImpl(repository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {
            whenever(repository.getPersons(""))
                .thenReturn(pagingSourceFactory)

            val result = getPersonsUseCase
                .invoke(GetPersonsUseCase.GetPersonsParams("", PagingConfig(20)))

            verify(repository).getPersons("")
            Assert.assertNotNull(result.first())
        }
}
