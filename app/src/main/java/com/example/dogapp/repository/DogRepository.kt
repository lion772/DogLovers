package com.example.dogapp.repository

import android.content.Context
import com.example.dogapp.model.DogBreed
import com.example.dogapp.network.DogApi

class DogRepository(private val context: Context, private val dogApi: DogApi): BaseRepository() {
    private val dogBreed:List<DogBreed>? = null

    suspend fun getDogs() = safeCallApi { dogApi.getDogsApi() }

}