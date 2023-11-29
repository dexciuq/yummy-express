package com.dexciuq.yummy_express.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.hideWithAnimation
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.common.showWithAnimation
import com.dexciuq.yummy_express.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(
                R.id.nav_host_fragment_container
            ) as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.productListFragment, R.id.productDetailFragment -> {
                    binding.bottomNavigationView.hideWithAnimation(binding.navHostFragmentContainer)
                }
                else -> {
                    binding.bottomNavigationView.showWithAnimation(binding.navHostFragmentContainer)
                }
            }
        }
    }
}