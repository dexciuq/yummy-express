package com.dexciuq.yummy_express.presentation.screen.profile.order_detail

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
import com.dexciuq.yummy_express.databinding.FragmentOrderDetailBinding
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {

    private val binding by lazy { FragmentOrderDetailBinding.inflate(layoutInflater) }
    private val viewModel: OrderDetailViewModel by viewModels()
    private val args: OrderDetailFragmentArgs by navArgs()
    private lateinit var adapter: OrderDetailAdapter

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getOrder(args.id)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        collectData()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.title = "Order #${args.id}"
    }

    private fun setupRecyclerView() {
        adapter = OrderDetailAdapter(imageLoader)
        binding.productList.adapter = adapter
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.order.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.productList.hide()
                        binding.productListLoading.show()
                        binding.productListLoading.startShimmer()
                    }

                    is Resource.Success -> {
                        binding.productListLoading.hide()
                        binding.productList.show()
                        binding.productListLoading.stopShimmer()
                        adapter.submitList(resource.data.orderItemList)
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                        Timber.i(resource.throwable)
                    }
                }
            }
        }
    }
}