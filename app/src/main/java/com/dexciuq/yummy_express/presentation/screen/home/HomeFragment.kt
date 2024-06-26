package com.dexciuq.yummy_express.presentation.screen.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.setTextSize
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentHomeBinding
import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.Filter
import com.dexciuq.yummy_express.presentation.activity.main.MainActivity
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import com.dexciuq.yummy_express.presentation.screen.home.banner.BannerViewPagerAdapter
import com.dexciuq.yummy_express.presentation.screen.product_list.ProductListAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val onNavigationItemChanger by lazy { requireActivity() as MainActivity }
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var categoriesAdapter: HomeCategoriesAdapter
    private lateinit var featuredProductsAdapter: ProductListAdapter

    @Inject
    lateinit var imageLoader: ImageLoader

    private val audioLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchVoiceSearchBottomSheetFragment()
        } else {
            Snackbar.make(
                binding.root,
                "To use this feature need to access your microphone",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupCategoriesSection()
        setupFeaturedProductsSection()
        collectData()
    }

    private fun setupSearchView() {
        binding.searchVoiceBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                launchVoiceSearchBottomSheetFragment()
            } else {
                requestAudioPermission()
            }
        }

        binding.searchView.setTextSize(14f)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToProductListFragment(
                        filter = Filter(name = query)
                    )
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search text change
                return true
            }
        })
    }

    private fun requestAudioPermission() {
        audioLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    private fun setupCategoriesSection() {
        categoriesAdapter = HomeCategoriesAdapter(imageLoader) {
            val action = HomeFragmentDirections.actionHomeFragmentToProductListFragment(
                filter = Filter(category = it)
            )
            findNavController().navigate(action)
        }

        binding.categoriesRv.adapter = categoriesAdapter
        binding.categoriesAll.setOnClickListener {
            onNavigationItemChanger.navigate(R.id.nav_graph_categories)
        }
    }

    private fun setupFeaturedProductsSection() {
        featuredProductsAdapter = ProductListAdapter(
            imageLoader = imageLoader,
            onItemClick = { product ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(
                        product.id
                    )
                )
            },
            onAddToCart = viewModel::addProductToCart,
            onDeleteFromCart = viewModel::removeProductFromCart,
            onUpdateAmountClick = viewModel::updateAmount,
        )
        binding.featuredProductsRv.adapter = featuredProductsAdapter
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.banners.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        setupBannerViewPager(resource.data)
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
                        binding.categoriesRv.hide()
                        binding.categoriesLoading.show()
                        binding.categoriesLoading.startShimmer()
                        delay(500)
                    }

                    is Resource.Success -> {
                        binding.categoriesLoading.hide()
                        binding.categoriesRv.show()
                        binding.categoriesLoading.stopShimmer()
                        categoriesAdapter.submitList(resource.data)
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
                        binding.featuredProductsRv.hide()
                        binding.featuredProductsLoading.show()
                        binding.featuredProductsLoading.startShimmer()
                        delay(500)
                    }

                    is Resource.Success -> {
                        binding.featuredProductsLoading.hide()
                        binding.featuredProductsRv.show()
                        binding.featuredProductsLoading.stopShimmer()
                        featuredProductsAdapter.submitList(resource.data)
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }
                }
            }
        }
    }

    private fun setupBannerViewPager(bannerList: List<Banner>) {
        val adapter = BannerViewPagerAdapter(requireActivity(), bannerList)

        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 1

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
    }

    private fun launchVoiceSearchBottomSheetFragment() {
        val voiceSearchBottomSheetFragment =
            VoiceSearchBottomSheetFragment.newInstance(::setTextToSearchView)
        voiceSearchBottomSheetFragment.show(
            parentFragmentManager,
            voiceSearchBottomSheetFragment.tag
        )
    }

    private fun setTextToSearchView(input: String) {
        binding.searchView.setQuery(input, true)
    }
}