package com.dexciuq.yummy_express.presentation.screen.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dexciuq.yummy_express.databinding.FragmentHomeBinding
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.utils.Resource
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
        return binding.root
    }

    private fun collectData() {
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