package com.dexciuq.yummy_express.presentation.screen.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentProductListBinding
import com.dexciuq.yummy_express.domain.model.Filter
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val binding by lazy { FragmentProductListBinding.inflate(layoutInflater) }
    private val viewModel: ProductListViewModel by viewModels()
    private val args: ProductListFragmentArgs by navArgs()
    private val filter: Filter by lazy { args.filter }

    private lateinit var productListAdapter: ProductListAdapter

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupAppBarSection()
        setupFilterSection()
        setupProductListSection()
        collectData()
        return binding.root
    }

    private fun setupAppBarSection() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.title = filter.category?.name ?: "Search for: \"${filter.name?.lowercase()}\""
    }

    private fun setupProductListSection() {
        productListAdapter = ProductListAdapter(
            imageLoader = imageLoader,
            onItemClick = { product ->
                findNavController().navigate(
                    ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                        product.id
                    )
                )
            },
            onAddToCart = viewModel::addProductToCart,
            onDeleteFromCart = viewModel::removeProductFromCart,
            onUpdateAmountClick = viewModel::updateAmount,
        )
        binding.productListRv.adapter = productListAdapter
    }

    private fun setupFilterSection() {
        binding.sortButton.setOnClickListener {
            launchSortBottomSheetFragment()
        }
    }

    private fun launchSortBottomSheetFragment() {
        val sortBottomSheetFragment = ProductListSortBottomSheetFragment.newInstance(filter) {
            filter.sort = it
            makeSearch(filter)
        }
        sortBottomSheetFragment.show(
            parentFragmentManager,
            sortBottomSheetFragment.tag
        )
    }

    private fun makeSearch(filter: Filter) {
        viewModel.getProductListByFilter(filter)
    }

    private fun collectData() {
        if (viewModel.products.value !is Resource.Success) {
            makeSearch(args.filter)
        }

        lifecycleScope.launch {
            viewModel.products.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.emptyProductList.hide()
                        binding.productListRv.hide()
                        binding.filterContainer.hide()
                        binding.productListLoading.show()
                        binding.productListLoading.startShimmer()
                        delay(300)
                    }

                    is Resource.Success -> {
                        binding.productListLoading.hide()
                        binding.productListRv.show()
                        binding.filterContainer.show()
                        binding.productListLoading.stopShimmer()
                        productListAdapter.submitList(resource.data)

                        if (resource.data.isEmpty()) {
                            binding.filterContainer.hide()
                            binding.productListRv.hide()
                            binding.emptyProductList.show()
                        }
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }
                }
            }
        }
    }
}