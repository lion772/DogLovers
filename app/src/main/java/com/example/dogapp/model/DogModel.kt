package com.example.dogapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog_table")
class DogModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0

    @ColumnInfo(name = "dog_breed")
    var dogbreed:String = ""

    @ColumnInfo(name = "life_span")
    var lifeSpan: String = ""
}