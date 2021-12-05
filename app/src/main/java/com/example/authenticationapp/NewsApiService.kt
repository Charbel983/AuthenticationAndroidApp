package com.example.authenticationapp

import com.example.authenticationapp.parsedData.News
import retrofit2.Call
import retrofit2.http.GET

const val API = "7.json?api-key=mOoQ3imMIAFVNiI73Ki4d18zRjuJAh7V"

interface NewsApiService {
    @GET(API)
    fun fetchAllNews () : Call<News>
}