package com.example.dogapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DogBreed(
    @SerializedName(value = "id")
    val breedId: String?,

    @SerializedName(value = "name")
    val dogBreed: String,

    @SerializedName(value = "life_span")
    val lifeSpan: String,

    @SerializedName(value = "breed_group")
    val breedGroup: String?,

    @SerializedName(value = "bred_for")
    val bredFor: String?,
    val temperament: String?,

    @SerializedName(value = "url")
    val imageUrl: String?
): Parcelable