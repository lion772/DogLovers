package com.example.dogapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapp.R
import com.example.dogapp.model.DogBreed
import com.example.dogapp.view.ListFragmentDirections

class DogsListAdapter(private var dogList: ArrayList<DogBreed> = arrayListOf()):
    RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {

    var onClickDog:((DogBreed) -> Unit)? = null

    fun updateDogList(newDogList: List<DogBreed>){
        dogList.clear()
        dogList.addAll(newDogList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        return DogViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.list_of_dogs, parent, false))
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int)
            = holder.bind(position)

    override fun getItemCount()= dogList.size

    inner class DogViewHolder(view:View): RecyclerView.ViewHolder(view){

        init {
            itemView.setOnClickListener{
                onClickDog?.invoke(dogList[adapterPosition])
            }
        }

        fun bind(position: Int){
            val breed = dogList[position]
            val image = itemView.findViewById<ImageView>(R.id.iv_dog)
            val nome = itemView.findViewById<TextView>(R.id.tv_dog_name)
            val idade = itemView.findViewById<TextView>(R.id.tv_dog_age)

            nome.text = breed.dogBreed
            idade.text = breed.lifeSpan

            itemView.setOnClickListener{
                val action = ListFragmentDirections.actionDetailFragment(breed.toString())
                action.apply {
                    dogBreed = breed.dogBreed
                    temperament = breed.temperament.toString()
                    lifeSpan = breed.lifeSpan
                }
                Navigation.findNavController(it).navigate(action)
            }
        }
    }
}