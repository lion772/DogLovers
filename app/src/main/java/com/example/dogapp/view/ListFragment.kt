package com.example.dogapp.view

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogapp.R
import com.example.dogapp.adapter.DogsListAdapter
import com.example.dogapp.model.DogBreed
import com.example.dogapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private val dogList: List<DogBreed> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getActionBar()?.title = "Dogs"
    }


    fun getActionBar(): androidx.appcompat.app.ActionBar? {
        return (activity as MainActivity?)?.supportActionBar
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refresh()
        setAdapter()

        refreshLayout.setOnRefreshListener {
            rv_dogs.visibility = View.GONE
            tv_error.visibility = View.GONE
            progressBar.visibility = View.GONE
            viewModel.refreshByPassCache()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogs.observe(viewLifecycleOwner) { dogs->
            dogs?.let {
                rv_dogs.visibility = View.VISIBLE
                (rv_dogs.adapter as DogsListAdapter).updateDogList(dogs)
            }
        }

        viewModel.dogsLoadErrorMessage.observe(viewLifecycleOwner) { isErrorMessage->
            isErrorMessage?.let {
                tv_error.text = it
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading->
            isLoading?.let {
                progressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it){
                    rv_dogs.visibility = View.GONE
                    tv_error.visibility = View.GONE
                }
            }
        }
    }

    private fun setAdapter(){
        rv_dogs.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = DogsListAdapter()
        }
        (rv_dogs.adapter as DogsListAdapter).onClickDog = { Dog ->
            Toast.makeText(activity, "Nome do cachorro: ${Dog.dogBreed}", Toast.LENGTH_SHORT).show()
        }
    }
}