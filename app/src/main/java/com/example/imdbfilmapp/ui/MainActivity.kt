package com.example.imdbfilmapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.example.imdbfilmapp.R
import com.example.imdbfilmapp.databinding.ActivityMainBinding
import com.example.imdbfilmapp.util.ToolbarChangesListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToolbarChangesListener {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var fragmentsFactory: MovieFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentsFactory
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController


    }

    override fun toolbarName(title: String) {
        binding.toolbarTitle.text = title
    }

    override fun toolbarBackButton(isDetailFragment: Boolean) {
        if (isDetailFragment){
            binding.backButton.visibility = View.VISIBLE
        }else{
            binding.backButton.visibility = View.GONE
        }
    }

}