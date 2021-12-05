package com.example.authenticationapp.parsedData


import com.google.gson.annotations.SerializedName

data class Result (
    val `abstract`: String,
    val media: List<Media>,

    @SerializedName("published_date")
    val publishedDate: String,
    val title: String,
    val url: String
)