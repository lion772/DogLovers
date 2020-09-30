package com.example.dogapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapp.R
import com.example.dogapp.databinding.ListOfDogsBinding
import com.example.dogapp.model.DogBreed
import com.example.dogapp.view.DogClickListener
import com.example.dogapp.view.ListFragmentDirections
import kotlinx.android.synthetic.main.list_of_dogs.view.*

class DogsListAdapter(private var dogList: ArrayList<DogBreed> = arrayListOf()):
    RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {

    var onClickDog: ((DogBreed) -> Unit)? = null

    fun updateDogList(newDogList: List<DogBreed>) {
        dogList.clear()
        dogList.addAll(newDogList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DogViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.list_of_dogs, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = dogList.size

    inner class DogViewHolder(var view: ListOfDogsBinding) : RecyclerView.ViewHolder(view.root),
        DogClickListener {

        init {
            itemView.setOnClickListener {
                onClickDog?.invoke(dogList[adapterPosition])
            }
        }

        fun bind(position: Int) {
            view.dog = dogList[position]
            view.listener = this

           /* val breed = dogList[position]
            val image = itemView.findViewById<ImageView>(R.id.iv_dog)
            val nome = itemView.findViewById<TextView>(R.id.tv_dog_name)
            val idade = itemView.findViewById<TextView>(R.id.tv_dog_age)

            nome.text = breed.dogBreed
            idade.text = breed.lifeSpan
            image.loadImage(breed.imageUrl, getProgressDrawable(image.context))*/
            //Glide.with(itemView).load(breed.imageUrl).into(image)

            /*itemView.setOnClickListener{
                val action = ListFragmentDirections.actionDetailFragment(breed.uuid)
                action.apply {
                    //val uuid = itemView.dogId.text.toString().toInt()
                    dogUuid = breed.uuid
                }
                Navigation.findNavController(it).navigate(action)
            }*/
        }

        override fun onDogClicked(view: View) {
            val uuid = view.tv_dog_id.text.toString().toInt()
            val action = ListFragmentDirections.actionDetailFragment(uuid)
            action.apply {
                dogUuid = uuid
            }
            Navigation.findNavController(view).navigate(action)
        }
    }
}