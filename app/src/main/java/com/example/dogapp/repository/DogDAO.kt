package com.example.dogapp.repository

import androidx.room.*
import com.example.dogapp.model.DogBreed
import java.util.*

@Dao
interface DogDAO {
    @Insert suspend fun insertAll(vararg dogs: DogBreed): List<Long>
    //@Update suspend fun update(dog: DogBreed): Int
    //@Delete suspend fun delete(dog: DogBreed): Int

    @Query("SELECT * FROM dog_table")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dog_table WHERE uuid =:dogId")
    suspend fun getDog(dogId:Int): DogBreed

    @Query("DELETE FROM dog_table")
    suspend fun deleteAllDogs()

}