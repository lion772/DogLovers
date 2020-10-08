package com.example.dogapp.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dogapp.dogcase.DogUseCase
import com.example.dogapp.model.DogBreed
import com.example.dogapp.network.ApiResult
import com.example.dogapp.repository.DogDataBase
import com.example.dogapp.util.NotificationsHelper
import com.example.dogapp.util.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import java.util.prefs.PreferenceChangeEvent

class MainViewModel(private val dogUseCase: DogUseCase, application: Application,
): BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val _dogs = MutableLiveData<List<DogBreed>>()
    val dogs: LiveData<List<DogBreed>> get() = _dogs

    private val _dogsLoadErrorMessage = MutableLiveData<String>()
    val dogsLoadErrorMessage: LiveData<String> get() = _dogsLoadErrorMessage

    private val _dogsLoadError = MutableLiveData<Boolean>()
    val dogsLoadError: LiveData<Boolean> get() = _dogsLoadError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun refresh() {
        checkCacheDuration()
        val updateTime = prefHelper.getUpdateTime()
        updateTime?.let {Time ->
            if (Time != 0L && System.nanoTime() - Time < refreshTime){
                fetchFromDatabase()
            } else{
                fetchDogsFromRemote()
            }
        }
    }

    private fun checkCacheDuration() {
        val cachePreference = prefHelper.getCacheDuration()
        try {
            val cachePreferenceInt = cachePreference?.toInt() ?: FIVE_MINUTES
            refreshTime = cachePreferenceInt.times(NANO_TO_SECONDS)
        } catch (e:NumberFormatException){
            e.printStackTrace()
        }
    }

    fun refreshByPassCache(){
        fetchDogsFromRemote()
    }

    private fun fetchFromDatabase(){
        _loading.value = true
        launch {
            val dogs = DogDataBase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)
        }
    }

    private fun fetchDogsFromRemote() {
        _loading.value = true
        launch {
            when (val response = dogUseCase.getDogsAwait()) {
                is ApiResult.Success -> {
                    response.data?.let { DogsList ->
                        storeDogsLocally(DogsList)
                        NotificationsHelper(getApplication()).createNotification()
                    }
                }
                is ApiResult.Error -> {
                    _dogsLoadErrorMessage.postValue(response.message)
                    _dogsLoadError.postValue(true)
                    _loading.postValue(false)
                }
            }
            _loading.postValue(false)
        }
    }

    private fun dogsRetrieved(dogList: List<DogBreed>) {
        _dogs.postValue(dogList)
        _dogsLoadError.postValue(false)
        _loading.postValue(false)
    }

    private fun storeDogsLocally(dogList: List<DogBreed>) {
        launch {
            val dao = DogDataBase(getApplication()).dogDao()
            dao.deleteAllDogs()
            val result = dao.insertAll(*dogList.toTypedArray()) //It gets a list and transforms into individual elements

            for (i in dogList.indices){
                dogList[i].uuid = result[i].toInt()
            }
            dogsRetrieved(dogList)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }


    companion object{
        //That's five minutes in nanoseconds
        private const val REFRESH_TIME = 5 * 60 * 1000 * 1000 * 1000L
        private const val FIVE_MINUTES = 5 * 60
        private const val NANO_TO_SECONDS = 1000 * 1000 * 1000L
    }


}