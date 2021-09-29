package com.example.imdbfilmapp.features.movies.data.entities

import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("Source")
    val source: String,
    @SerializedName("Value")
    val value: String
)