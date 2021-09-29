package com.example.imdbfilmapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdbfilmapp.R
import com.example.imdbfilmapp.adapter.HomeRecyclerViewAdapter
import com.example.imdbfilmapp.base.BaseFragment
import com.example.imdbfilmapp.databinding.FragmentHomeBinding
import com.example.imdbfilmapp.ui.MainActivity
import com.example.imdbfilmapp.util.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(
    private val recyclerViewAdapter: HomeRecyclerViewAdapter
) : BaseFragment<FragmentHomeBinding>() {

    private lateinit var viewModel: HomeViewModel

    private var job: Job? = null
    private var pageNum = 1

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        val toolbarListener = activity as MainActivity

        toolbarListener.toolbarName(binding.root.context.getString(R.string.app_name))
        toolbarListener.toolbarBackButton(false)

        binding.rvMovies.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(binding.root.context)
        }

        // recycler view için onScrollListener kurulacak pagination yapılıcak

        subscribeToStateFlow()

        recyclerViewAdapter.setOnMovieClickListener {
            toolbarListener.toolbarName(it.title)
            toolbarListener.toolbarBackButton(true)

            val action = HomeFragmentDirections.homeFragmentToDetailFragment(it.imdbId)
            findNavController().navigate(action)

        }


    }

    override fun onResume() {
        super.onResume()
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(500)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.getMovies(it.toString(), pageNum)
                    }
                }
            }
        }
    }

    private fun subscribeToStateFlow() {
        lifecycleScope.launch {
            viewModel.listMovie.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.homeProgressBar.visibility = View.GONE
                        binding.rvMovies.visibility = View.VISIBLE
                        it.data?.let { response ->
                            response.movies?.let { listMovie->
                                recyclerViewAdapter.listMovie = listMovie
                            } ?:
                                Snackbar.make(binding.root, response.error ?: "", Snackbar.LENGTH_LONG).show()
                            if (response.movies == null){
                                recyclerViewAdapter.listMovie = emptyList()
                            }
                        }
                    }
                    Status.LOADING -> {
                        binding.rvMovies.visibility = View.GONE
                        binding.homeProgressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.homeProgressBar.visibility = View.GONE
                        Toast.makeText(
                            binding.root.context,
                            it.message ?: "Error Occurred!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }


}