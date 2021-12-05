package com.example.authenticationapp.parsedData


import com.google.gson.annotations.SerializedName

data class Media (
    @SerializedName("media-metadata")
    val mediaMetadata: List<MediaMetadata>
)