package com.example.dogapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogapp.dogcase.DogUseCase
import com.example.dogapp.model.DogBreed

class DetailViewModel(private var useCase: DogUseCase): ViewModel() {

    private var _dogs = MutableLiveData<List<DogBreed>>()
    val dogs: LiveData<List<DogBreed>> get() = _dogs

    fun fetch(){

    }
}