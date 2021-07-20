package com.alecbrando.animeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alecbrando.animeapp.R
import com.alecbrando.animeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)

        setupActionBarWithNavController(navController)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.top_tab_bar, menu)
//        return true
//    }
}