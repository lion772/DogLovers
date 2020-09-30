package com.example.dogapp.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ActionBarContainer
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentDetailBinding
import com.example.dogapp.model.DogPallete
import com.example.dogapp.util.getProgressDrawable
import com.example.dogapp.util.loadImage
import com.example.dogapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private val viewModel:DetailViewModel by viewModel()
    private var dogUuid:String? = null
    private lateinit var binding:FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getActionBar()?.title = "Detail Screen"
    }

    fun getActionBar(): androidx.appcompat.app.ActionBar? {
        return (activity as MainActivity?)?.supportActionBar

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid.toString()
        }
        dogUuid?.toInt()?.let {DogId -> viewModel.fetch(DogId) }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogs.observe(this){ Dog ->
            Dog?.let {
                binding.dog = Dog
                it.imageUrl?.let {image ->
                    setupBackgroundColor(image)
                }
            }
        }
    }

    private fun setupBackgroundColor(url:String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate{palette ->
                        val intColor = palette?.lightMutedSwatch?.rgb  ?: palette?.darkVibrantSwatch?.rgb
                        val myPalette = intColor?.let { DogPallete(it) }
                        binding.palette = myPalette
                    }
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}