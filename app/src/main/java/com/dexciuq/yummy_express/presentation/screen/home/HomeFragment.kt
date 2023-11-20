package com.dexciuq.yummy_express.presentation.screen.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.setTextSize
import com.dexciuq.yummy_express.databinding.FragmentHomeBinding
import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.presentation.screen.home.banner.BannerViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment"

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
                        Toast.makeText(
                            requireContext(),
                            resource.throwable.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.featuredProducts.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.d(TAG, "Loading")
                    }

                    is Resource.Success -> {
                        val data: List<Product> = resource.data
                        Log.d(TAG, "data")
                        Log.d(TAG, data.toString())
                    }

                    is Resource.Error -> {
                        val throwable: Throwable = resource.throwable
                        Log.d(TAG, throwable.toString())
                    }
                }
            }
        }
    }
}