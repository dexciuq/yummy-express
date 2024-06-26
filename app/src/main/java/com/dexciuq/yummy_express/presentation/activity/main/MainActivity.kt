package com.dexciuq.yummy_express.presentation.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.hideWithAnimation
import com.dexciuq.yummy_express.common.showWithAnimation
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnNavigationItemChanger {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNavigationView()
        collectBadgeCount()
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
                R.id.addressFragment,
                R.id.checkoutFragment, -> {
                    binding.bottomNavigationView.hideWithAnimation(binding.navHostFragmentContainer)
                }

                else -> {
                    binding.bottomNavigationView.showWithAnimation(binding.navHostFragmentContainer)
                }
            }
        }
    }

    private fun collectBadgeCount() {
        lifecycleScope.launch {
            viewModel.cartItemCount.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        toast(getString(R.string.failed_to_load_item_count))
                    }

                    is Resource.Success -> {
                        when (resource.data) {
                            0 -> {
                                binding.bottomNavigationView.removeBadge(R.id.nav_graph_cart)
                            }

                            else -> {
                                binding.bottomNavigationView
                                    .getOrCreateBadge(R.id.nav_graph_cart)
                                    .number = resource.data

                                binding.bottomNavigationView
                                    .getOrCreateBadge(R.id.nav_graph_cart)
                                    .backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.badge)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun navigate(@IdRes menu: Int) {
        binding.bottomNavigationView.selectedItemId = menu
    }
}

