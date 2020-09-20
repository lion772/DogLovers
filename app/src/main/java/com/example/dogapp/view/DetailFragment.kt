package com.example.dogapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.dogapp.R
import com.example.dogapp.util.getProgressDrawable
import com.example.dogapp.util.loadImage
import com.example.dogapp.view.ListFragmentDirections.actionDetailFragment
import com.example.dogapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private val viewModel:DetailViewModel by viewModel()
    private var breedId:String? = null
    private var dogBreed:String? = null
    private var lifeSpan:String? = null
    private var breedGroup:String? = null
    private var bredFor:String? = null
    private var temperament:String? = null
    private var imageUrl:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_detail, container, false)
        //return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            breedId = DetailFragmentArgs.fromBundle(it).breedId
            tv_name.text = DetailFragmentArgs.fromBundle(it).dogBreed
            tv_lifespan.text = DetailFragmentArgs.fromBundle(it).lifeSpan
            breedGroup = DetailFragmentArgs.fromBundle(it).breedGroup
            bredFor = DetailFragmentArgs.fromBundle(it).bredFor
            tv_temperament.text = DetailFragmentArgs.fromBundle(it).temperament
            im_dog.loadImage(DetailFragmentArgs.fromBundle(it).dogImage, getProgressDrawable(im_dog.context))

        }
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.dogs.observe(this, Observer {Dog ->
            Dog?.let {  }
        })
    }

}