package com.dexciuq.yummy_express.presentation.screen.cart

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
import com.dexciuq.yummy_express.common.toMoney
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentCartBinding
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val binding by lazy { FragmentCartBinding.inflate(layoutInflater) }
    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartProductListAdapter

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupCheckoutButtonListener()
        setupCartProductListRecyclerView()
        collectData()
        return binding.root
    }

    private fun setupCheckoutButtonListener() {
        binding.checkoutButton.setOnClickListener {
            findNavController().navigate(
                CartFragmentDirections.actionCartFragmentToAddressFragment()
            )
        }
    }

    private fun setupCartProductListRecyclerView() {
        adapter = CartProductListAdapter(
            imageLoader = imageLoader,
            onDeleteClick = viewModel::removeProductFromCart,
            onUpdateAmountClick = viewModel::updateAmount
        )
        binding.cartProductList.adapter = adapter
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.cartProductList.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.emptyCart.hide()
                        binding.cartProductList.hide()
                        binding.checkoutContainer.hide()
                        binding.cartProductListLoading.show()
                        binding.cartProductListLoading.startShimmer()
                        delay(500)
                    }

                    is Resource.Success -> {
                        binding.cartProductListLoading.hide()
                        binding.cartProductListLoading.stopShimmer()
                        val productList = resource.data
                        when (productList.size) {
                            0 -> {
                                binding.emptyCart.show()
                                binding.cartProductList.hide()
                                binding.checkoutContainer.hide()
                            }

                            else -> {
                                binding.emptyCart.hide()
                                binding.cartProductList.show()
                                binding.checkoutContainer.show()
                            }
                        }
                        adapter.submitList(productList)
                        setupCheckoutContainer(productList)
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }
                }
            }
        }
    }

    private fun setupCheckoutContainer(productList: List<Product>) {
        val discountTotal = 600000L
        val subtotal = productList.sumOf {
            it.amount?.times(it.price)?.toLong() ?: 0L
        }

        val shippingCharges = if (subtotal > discountTotal) 0L else 30000L
        val total = shippingCharges + subtotal

        binding.subtotal.text = subtotal.toMoney()
        binding.shippingCharges.text = shippingCharges.toMoney()
        binding.total.text = total.toMoney()
    }
}