package com.example.dogapp.network

import com.example.dogapp.model.DogBreed
import retrofit2.Response
import retrofit2.http.GET

interface DogApi {

    @GET("DevTides/DogsApi/master/dogs.json")
    suspend fun getDogsApi(): Response<List<DogBreed>>
}