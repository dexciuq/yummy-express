package com.dexciuq.yummy_express.presentation.screen.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentCategoriesBinding
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private val binding by lazy { FragmentCategoriesBinding.inflate(layoutInflater) }
    private val viewModel: CategoriesViewModel by viewModels()
    private lateinit var adapter: CategoriesAdapter
    @Inject lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = CategoriesAdapter(imageLoader) {
            toast(it.toString())
        }
        binding.categoriesRv.adapter = adapter

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
                        adapter.submitList(resource.data)
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }
                }
            }
        }
        return binding.root
    }
}