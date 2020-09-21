package com.example.dogapp.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dogapp.model.DogBreed

@Database(entities = [DogBreed::class], version = 1) //entities = arrayOf(DogBreed::class, NextEntity::class, etc)
abstract class DogDataBase: RoomDatabase() {
    abstract fun dogDao(): DogDAO

    companion object{
        @Volatile private var instance: DogDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, DogDataBase::class.java, "dog_database"
        ).build()

    }
}