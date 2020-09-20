package com.example.dogapp

import android.app.Application
import com.example.dogapp.di.Module.moduleDI
import com.example.dogapp.di.Module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class DogApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DogApplication)
            modules(listOf(moduleDI, networkModule))
        }
    }
}