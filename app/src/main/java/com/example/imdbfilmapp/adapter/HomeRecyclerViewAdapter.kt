package com.example.imdbfilmapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbfilmapp.databinding.MovieRowBinding
import com.example.imdbfilmapp.features.movies.domain.entities.Movie
import com.example.imdbfilmapp.features.movies.domain.entities.Movies
import com.example.imdbfilmapp.util.downloadImage
import javax.inject.Inject

class HomeRecyclerViewAdapter @Inject constructor() :
    RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder>() {

    private var movieClickListener: ((Movie) -> Unit)? = null

    inner class HomeViewHolder(private val itemBinding: MovieRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: Movie) {
            itemBinding.ivMovies.downloadImage(data.poster, itemBinding.root.context)
            itemBinding.tvMoviesTitle.text = data.title
            itemBinding.tvMoviesYear.text = data.year
        }
    }

    fun setOnMovieClickListener(listener: (Movie) -> Unit) {
        movieClickListener = listener
    }

    private val differ = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
    private val asyncListDiffer = AsyncListDiffer(this, differ)

    var listMovie: List<Movie>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val rootView = MovieRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val movieInstance = listMovie[position]
        holder.bind(movieInstance)

        holder.itemView.setOnClickListener {
            movieClickListener?.let {
                it(movieInstance)
            }
        }
    }

    override fun getItemCount(): Int = listMovie.size
}