package com.example.imdbfilmapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.imdbfilmapp.adapter.HomeRecyclerViewAdapter
import com.example.imdbfilmapp.ui.detail.DetailFragment
import com.example.imdbfilmapp.ui.home.HomeFragment
import javax.inject.Inject

class MovieFragmentFactory @Inject constructor(
    private val recyclerViewAdapter: HomeRecyclerViewAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment(recyclerViewAdapter)
            DetailFragment::class.java.name -> DetailFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}