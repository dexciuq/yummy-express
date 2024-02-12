package com.dexciuq.yummy_express.presentation.screen.profile.my_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentMyOrdersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyOrdersFragment : Fragment() {

    private val binding by lazy { FragmentMyOrdersBinding.inflate(layoutInflater) }
    private val viewModel: MyOrdersViewModel by viewModels()
    private lateinit var adapter: MyOrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupCategoriesRecyclerView()
        collectData()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupCategoriesRecyclerView() {
        adapter = MyOrdersAdapter(
            onItemClick = {
                toast(it.id.toString())
            }
        )
        binding.myOrdersRv.adapter = adapter
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.orders.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.myOrdersRv.hide()
                        binding.myOrdersLoading.show()
                        binding.myOrdersLoading.startShimmer()
                    }

                    is Resource.Success -> {
                        binding.myOrdersLoading.hide()
                        binding.myOrdersRv.show()
                        binding.myOrdersLoading.stopShimmer()
                        adapter.submitList(resource.data)
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }
                }
            }
        }
    }
}