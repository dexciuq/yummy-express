package com.dexciuq.yummy_express.presentation.screen.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dexciuq.yummy_express.databinding.FragmentProductListBinding
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val binding by lazy { FragmentProductListBinding.inflate(layoutInflater) }
    private val viewModel: ProductListViewModel by viewModels()
    private val args: ProductListFragmentArgs by navArgs()
    @Inject lateinit var imageLoader: ImageLoader
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}