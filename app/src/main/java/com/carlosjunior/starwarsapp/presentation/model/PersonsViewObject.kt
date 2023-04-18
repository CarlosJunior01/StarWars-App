package com.carlosjunior.starwarsapp.presentation.model

import android.os.Parcelable
import com.carlosjunior.core.domain.model.Persons
import com.carlosjunior.starwarsapp.database.model.FavoritePerson
import com.carlosjunior.starwarsapp.database.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonsViewObject(
    val name: String?,
    val height: String?,
    val hairColor: String?,
    val skinColor: String?,
    val birthYear: String?,
    val gender: String?,
    val url: String?
) : Parcelable {

    constructor(person: Persons) : this(
        name = person.name,
        height = person.height,
        hairColor = person.hairColor,
        skinColor = person.skinColor,
        birthYear = person.birthYear,
        gender = person.gender,
        url = person.url
    )

    constructor(person: Person) : this(
        name = person.name,
        height = person.height,
        hairColor = person.hairColor,
        skinColor = person.skinColor,
        birthYear = person.birthYear,
        gender = person.gender,
        url = person.url
    )

    constructor(person: FavoritePerson) : this(
        name = person.name,
        height = person.height,
        hairColor = person.hairColor,
        skinColor = person.skinColor,
        birthYear = person.birthYear,
        gender = person.gender,
        url = person.url
    )
}

