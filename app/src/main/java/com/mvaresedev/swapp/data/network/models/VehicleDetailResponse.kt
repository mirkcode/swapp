package com.mvaresedev.swapp.data.network.models


import com.google.gson.annotations.SerializedName

data class VehicleDetailResponse(
    @SerializedName("cost_in_credits")
    val costInCredits: String,
    @SerializedName("crew")
    val crew: String,
    @SerializedName("films")
    val films: List<String>,
    @SerializedName("length")
    val length: String,
    @SerializedName("manufacturer")
    val manufacturer: String,
    @SerializedName("max_atmosphering_speed")
    val maxAtmospheringSpeed: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("vehicle_class")
    val vehicleClass: String
)