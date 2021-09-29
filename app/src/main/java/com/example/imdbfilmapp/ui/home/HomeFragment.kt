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
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbfilmapp.R
import com.example.imdbfilmapp.adapter.HomeRecyclerViewAdapter
import com.example.imdbfilmapp.base.BaseFragment
import com.example.imdbfilmapp.databinding.FragmentHomeBinding
import com.example.imdbfilmapp.features.movies.domain.entities.Movie
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
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var loading = true
    private var previousTotal = 0
    private var searchText = ""
    private val moviesList = ArrayList<Movie>()

    private var job: Job? = null
    private var pageNum = 1

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        val toolbarListener = activity as MainActivity

        toolbarListener.toolbarName(binding.root.context.getString(R.string.app_name))
        toolbarListener.toolbarBackButton(false)

        subscribeToStateFlow()

        recyclerViewAdapter.setOnMovieClickListener {
            toolbarListener.toolbarName(it.title)
            toolbarListener.toolbarBackButton(true)

            val action = HomeFragmentDirections.homeFragmentToDetailFragment(it.imdbId)
            findNavController().navigate(action)
        }

        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    val pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false
                            previousTotal = totalItemCount
                        }
                    }
                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        pageNum++
                        job?.cancel()
                        job = lifecycleScope.launch {
                            viewModel.getMovies(searchText, pageNum)
                        }
                        loading = true
                    }
                }
            }
        }

        binding.rvMovies.apply {
            adapter = recyclerViewAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(scrollListener)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        pageNum = 1
                        searchText = it.toString()
                        viewModel.getMovies(searchText, pageNum)
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
                            response.movies?.let { listMovie ->
                                moviesList.addAll(listMovie)
                                recyclerViewAdapter.listMovie = moviesList
                            } ?: Snackbar.make(
                                binding.root,
                                response.error ?: "An Error Occured!",
                                Snackbar.LENGTH_LONG
                            ).show()
                            if (response.movies == null) {
                                recyclerViewAdapter.listMovie = emptyList()
                            }
                        }
                        if (it.data == null || it.data.movies == null){
                            binding.tvSearchMovieAlert.visibility = View.VISIBLE
                        }else{
                            binding.tvSearchMovieAlert.visibility = View.GONE
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