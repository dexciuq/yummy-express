package com.dexciuq.yummy_express.presentation.screen.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.common.toMoney
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentProductDetailBinding
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val binding by lazy { FragmentProductDetailBinding.inflate(layoutInflater) }
    private val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()
    @Inject lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        collectData()
        viewModel.getProduct(args.id)
        return binding.root
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.product.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.imageProgressBar.show()
                    }

                    is Resource.Success -> {
                        binding.imageProgressBar.hide()
                        setupProductSection(resource.data)
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }
                }
            }
        }
    }

    private fun setupProductSection(product: Product) {
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        postponeEnterTransition(200, TimeUnit.MILLISECONDS)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        imageLoader.load(product.imageURL, binding.productImage)

        binding.name.text = product.name
        binding.price.text = "${product.price.toMoney()} / ${product.unit}"
        binding.description.text = product.description
        binding.category.text = getString(R.string.detail_category, product.category.name)
        binding.brand.text = getString(R.string.detail_brand, product.brand)
        binding.country.text = getString(R.string.detail_country, product.country)
    }

}