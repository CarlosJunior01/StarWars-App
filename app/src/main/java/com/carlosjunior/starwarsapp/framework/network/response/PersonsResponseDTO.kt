package com.carlosjunior.starwarsapp.framework.network.response

import com.carlosjunior.core.domain.model.Persons
import com.google.gson.annotations.SerializedName

data class PersonsResponseDTO(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<Results>
)

data class Results(
    @SerializedName("name") val name: String?,
    @SerializedName("height") val height: String?,
    @SerializedName("hair_color") val hairColor: String?,
    @SerializedName("skin_color") val skinColor: String?,
    @SerializedName("eye_color") val eyeColor: String?,
    @SerializedName("birth_year") val birthYear: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("url") val url: String?
)

fun Results.toPersonsModel(): Persons {
    return Persons(
        name = this.name,
        height = this.height,
        hairColor = this.hairColor,
        skinColor = this.skinColor,
        eyeColor = this.eyeColor,
        birthYear = this.birthYear,
        gender = this.gender,
        url = this.url
    )
}
