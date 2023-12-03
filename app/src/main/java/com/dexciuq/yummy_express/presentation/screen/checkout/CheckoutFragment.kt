package com.dexciuq.yummy_express.presentation.screen.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.common.toMoney
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentCheckoutBinding
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.presentation.activity.MainActivity
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private val binding by lazy { FragmentCheckoutBinding.inflate(layoutInflater) }
    private val onNavigationItemChanger by lazy { requireActivity() as MainActivity }
    private val viewModel: CheckoutViewModel by viewModels()
    private val args: CheckoutFragmentArgs by navArgs()
    private lateinit var adapter: CheckoutProductListAdapter

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupToolbar()
        setupAddressContainer()
        setupCheckoutProductList()
        setupOrderButtonListener()
        collectData()
        return binding.root
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupAddressContainer() {
        val address = args.address
        binding.street.text = getString(R.string.street, address.street)
        binding.apartment.text = getString(R.string.apartment_with_value, address.apartment)
        binding.entrance.text = getString(R.string.entrance_with_value, address.entrance)
        binding.floor.text = getString(R.string.floor_with_value, address.floor)
    }

    private fun setupOrderButtonListener() {
        binding.orderButton.setOnClickListener {
            Snackbar.make(
                it,
                getString(R.string.your_order_was_successfully_placed),
                Snackbar.LENGTH_SHORT
            ).show()
            viewModel.clearCart()
            findNavController().navigate(
                CheckoutFragmentDirections.actionCheckoutFragmentToCartFragment()
            )
            onNavigationItemChanger.navigate(R.id.nav_graph_profile)
        }
    }

    private fun setupCheckoutProductList() {
        adapter = CheckoutProductListAdapter(imageLoader)
        binding.productList.adapter = adapter
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.productList.collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }

                    is Resource.Loading -> {
                        binding.productList.hide()
                        binding.productListLoading.show()
                        binding.productListLoading.startShimmer()
                        delay(500)
                    }

                    is Resource.Success -> {
                        binding.productListLoading.hide()
                        binding.productListLoading.stopShimmer()
                        binding.productList.show()
                        setupPaymentContainer(resource.data)
                        adapter.submitList(resource.data)
                    }
                }
            }
        }
    }

    private fun setupPaymentContainer(productList: List<Product>) {
        val discountTotal = 600000L
        val subtotal = productList.sumOf {
            it.amount?.times(it.price)?.toLong() ?: 0L
        }

        val shippingCharges = if (subtotal > discountTotal) 0L else 30000L
        val total = shippingCharges + subtotal

        binding.payment.text = total.toMoney()
    }
}