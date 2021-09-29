package com.example.imdbfilmapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.imdbfilmapp.base.BaseFragment
import com.example.imdbfilmapp.databinding.FragmentDetailBinding
import com.example.imdbfilmapp.util.Status
import com.example.imdbfilmapp.util.downloadImage
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

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
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.detailProgressBar.visibility = View.GONE
                        binding.scrollView.visibility = View.VISIBLE

                        it.data?.let { response ->
                            binding.apply {
                                ivMovie.downloadImage(
                                    response.poster,binding.root.context
                                )
                                tvMovieCast.text = response.actors
                                tvMovieCountry.text = response.country
                                tvMovieDescription.text = response.plot
                                tvMovieDirector.text = response.director
                                tvMovieGenre.text = response.genre
                                tvMovieImdbRating.text = response.imdbRating
                                tvMovieImdbVotes.text = response.imdbVotes
                                tvMovieLanguage.text = response.language
                                tvMovieMetaScore.text = response.metaScore
                                tvMovieReleasedDate.text = response.released
                                tvMovieRunTime.text = response.runtime
                                try {
                                    tvMovieRatingsName1.text = response.ratingResponse[0].source
                                    tvMovieRatingsName2.text = response.ratingResponse[1].source
                                    tvMovieRatingsName3.text = response.ratingResponse[2].source
                                    tvMovieRatingsValue1.text = response.ratingResponse[0].value
                                    tvMovieRatingsValue2.text = response.ratingResponse[1].value
                                    tvMovieRatingsValue3.text = response.ratingResponse[2].value
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }

                    }
                    Status.LOADING -> {
                        binding.detailProgressBar.visibility = View.VISIBLE
                        binding.scrollView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.detailProgressBar.visibility = View.GONE
                        Snackbar.make(
                            binding.root,
                            it.message ?: "Error Occurred!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

}