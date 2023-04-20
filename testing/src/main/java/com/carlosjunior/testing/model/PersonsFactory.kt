package com.carlosjunior.testing.model

import com.carlosjunior.core.domain.model.Persons

class PersonsFactory {

    fun create(persons: PersonsClass) = when (persons) {
        PersonsClass.Luke -> Persons(
            name = "Luke Skywalke",
            height = "172",
            hairColor = "blond",
            skinColor = "fair",
            eyeColor = "blue",
            birthYear = "19BBY",
            gender = "male",
            url = ""
        )
        PersonsClass.Darth -> Persons(
            name = "Darth Vader",
            height = "202",
            hairColor = "none",
            skinColor = "white",
            eyeColor = "yellow",
            birthYear = "41.9BBY",
            gender = "male",
            url = ""
        )
    }

    sealed class PersonsClass {
        object Luke: PersonsClass()
        object Darth: PersonsClass()
    }
}