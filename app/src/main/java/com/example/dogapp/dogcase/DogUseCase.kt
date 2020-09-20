package com.example.dogapp.dogcase

import com.example.dogapp.repository.DogRepository
import com.example.dogapp.model.DogBreed

class DogUseCase(private val dogRepository: DogRepository) {

    suspend fun getDogsAwait() = dogRepository.getDogs()

}