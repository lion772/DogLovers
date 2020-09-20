package com.example.dogapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.dogcase.DogUseCase
import com.example.dogapp.model.DogBreed
import com.example.dogapp.network.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val dogUseCase: DogUseCase): ViewModel() {

    /*val dogs: LiveData<DogBreed> = live {
    emit(dogUseCase.getDogsAwait())
}*/

    private val _dogs = MutableLiveData<List<DogBreed>>()
    val dogs: LiveData<List<DogBreed>> get() = _dogs

    private val _dogsLoadError = MutableLiveData<String>()
    val dogsLoadError:LiveData<String> get() = _dogsLoadError

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get() = _loading


    fun refresh(){
        getDogs()
    }

    private fun getDogs(){
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = dogUseCase.getDogsAwait()){
                is ApiResult.Success -> _dogs.postValue(response.data)
                is ApiResult.Error -> _dogsLoadError.postValue(response.message)
            }
            _loading.postValue(false)
        }

    }
    // TODO: 16/09/2020

}