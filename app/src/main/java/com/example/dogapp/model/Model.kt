package com.example.dogapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "dog_table")
data class DogBreed(
    @ColumnInfo(name = "breed_id")
    @SerializedName(value = "id")
    val breedId: String?,

    @ColumnInfo(name = "dog_name")
    @SerializedName(value = "name")
    val dogBreed: String,

    @ColumnInfo(name = "life_span")
    @SerializedName(value = "life_span")
    val lifeSpan: String,

    @ColumnInfo(name = "breed_group")
    @SerializedName(value = "breed_group")
    val breedGroup: String?,

    @ColumnInfo(name = "bred_for")
    @SerializedName(value = "bred_for")
    val bredFor: String?,

    @ColumnInfo(name = "temperament")
    val temperament: String?,

    @ColumnInfo(name = "dog_url")
    @SerializedName(value = "url")
    val imageUrl: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}

/*Todas as variáveis são strings e vem do Backend, toda vez que criar um dogBreed criará
   * um ID também se colocar dentro do construtor. A primary key só será instanciada pelo room
   * database quando a entidade for colocada dentro dele.*/

data class DogPallete(var color: Int)