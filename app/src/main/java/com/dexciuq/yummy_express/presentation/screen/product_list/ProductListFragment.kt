package com.dexciuq.yummy_express.presentation.screen.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
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
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val binding by lazy { FragmentProductListBinding.inflate(layoutInflater) }
    private val viewModel: ProductListViewModel by viewModels()
    private val args: ProductListFragmentArgs by navArgs()
    private lateinit var productListAdapter: ProductListAdapter
    @Inject lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postponeEnterTransition()
        setupAppBarSection()
        setupProductListSection()
        collectData()
        viewModel.getProductListByCategory(args.category.id)
        return binding.root
    }

    private fun setupAppBarSection() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.title = args.category.name
    }

    private fun setupProductListSection() {
        productListAdapter = ProductListAdapter(
            imageLoader = imageLoader,
            onItemClick = { product, extras ->
                val action = ProductListFragmentDirections
                    .actionProductListFragmentToProductDetailFragment(product.id)
                findNavController().navigate(action, extras)
            },
            onAddToCart = viewModel::addProductToCart,
            onDeleteFromCart = viewModel::removeProductFromCart,
            onUpdateAmountClick = viewModel::updateAmount,
        )
        binding.productListRv.adapter = productListAdapter
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.products.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.emptyProductList.hide()
                        binding.productListRv.hide()
                        binding.productListLoading.show()
                        binding.productListLoading.startShimmer()
                    }

                    is Resource.Success -> {
                        binding.productListLoading.hide()
                        binding.productListRv.show()
                        binding.productListLoading.stopShimmer()
                        productListAdapter.submitList(resource.data)
                        if (resource.data.isEmpty()) {
                            binding.productListRv.hide()
                            binding.emptyProductList.show()
                        }
                        binding.productListRv.doOnPreDraw {
                            startPostponedEnterTransition()
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