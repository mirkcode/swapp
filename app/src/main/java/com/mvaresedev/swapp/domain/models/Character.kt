package com.mvaresedev.swapp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val birthYear: String,
    val eyeColor: String?,
    val films: List<Int>,
    val gender: String?,
    val hairColor: String?,
    val height: String,
    val mass: String,
    val name: String,
    val skinColor: String,
    val vehicles: List<Int>,
    val avatarUrl: String
): Parcelable