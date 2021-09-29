package com.example.imdbfilmapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.imdbfilmapp.base.BaseFragment
import com.example.imdbfilmapp.databinding.FragmentDetailBinding
import com.example.imdbfilmapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private lateinit var viewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        lifecycleScope.launchWhenResumed {
            viewModel.getSelectedMovie(args.imdbId)
        }

        subscribeToStateFlow()
    }

    private fun subscribeToStateFlow() {
        lifecycleScope.launch {
            viewModel.resultMovie.collect {
                when(it.status){
                    Status.SUCCESS -> {

                    }
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {

                    }
                }
            }
        }
    }

}