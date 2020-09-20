package com.example.dogapp.repository

import androidx.room.*
import com.example.dogapp.model.DogModel

@Dao
interface DogDAO {
    @Insert fun save(user: DogModel): Long
    @Update fun update(user: DogModel): Int
    @Delete fun delete(user: DogModel): Int
    @Query("SELECT * FROM dog_table WHERE dog_breed = :dogBreed AND life_span = :lifeSpan")
    fun getUser(dogBreed:String, lifeSpan:String): DogModel


}