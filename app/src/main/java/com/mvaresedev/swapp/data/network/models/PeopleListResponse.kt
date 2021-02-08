package com.mvaresedev.swapp.data.network.models


import com.google.gson.annotations.SerializedName

data class PeopleListResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("birth_year")
        val birthYear: String,
        @SerializedName("eye_color")
        val eyeColor: String,
        @SerializedName("films")
        val films: List<String>,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("hair_color")
        val hairColor: String,
        @SerializedName("height")
        val height: String,
        @SerializedName("mass")
        val mass: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("skin_color")
        val skinColor: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("vehicles")
        val vehicles: List<String>
    )
}