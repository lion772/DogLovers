package com.example.dogapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dogapp.dogcase.DogUseCase
import com.example.dogapp.model.DogBreed
import com.example.dogapp.repository.DogDataBase
import kotlinx.coroutines.launch

class DetailViewModel(private var useCase: DogUseCase, application: Application): BaseViewModel(application) {

    private var _dogs = MutableLiveData<DogBreed>()
    val dogs: LiveData<DogBreed> get() = _dogs

    fun fetch(uuid:Int){
        launch {
            _dogs.postValue(DogDataBase(getApplication()).dogDao().getDog(uuid))
        }
    }
}