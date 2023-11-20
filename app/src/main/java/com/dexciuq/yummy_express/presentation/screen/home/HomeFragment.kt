package com.dexciuq.yummy_express.presentation.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.setTextSize
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentHomeBinding
import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.presentation.screen.home.banner.BannerViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        collectData()
        setupSearchView()
        return binding.root
    }

    private fun setupBannerViewPager(bannerList: List<Banner>) {
        val adapter = BannerViewPagerAdapter(requireActivity(), bannerList)

        binding.viewPager.adapter = adapter
        binding.viewPager.currentItem = bannerList.size / 2
        binding.viewPager.offscreenPageLimit = 1

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
    }

    private fun setupSearchView() {
        binding.searchView.setTextSize(14f)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search submission
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search text change
                return true
            }
        })
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.banners.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        setupBannerViewPager(bannerList = resource.data)
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.categories.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.categoriesLoading.show()
                        binding.categoriesLoading.startShimmer()
                        delay(10000)
                    }

                    is Resource.Success -> {
                        binding.categoriesLoading.hide()
                        binding.categoriesLoading.stopShimmer()
                        val data: List<Category> = resource.data
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.featuredProducts.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.featuredProductsLoading.show()
                        binding.featuredProductsLoading.startShimmer()
                        delay(10000)
                    }

                    is Resource.Success -> {
                        binding.featuredProductsLoading.hide()
                        binding.featuredProductsLoading.stopShimmer()
                        val data: List<Product> = resource.data
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }
                }
            }
        }
    }

}