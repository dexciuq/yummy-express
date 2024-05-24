package com.dexciuq.yummy_express.presentation.screen.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.domain.model.Address
import com.dexciuq.yummy_express.domain.model.Order
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.use_case.order.MakeOrderUseCase
import com.dexciuq.yummy_express.domain.use_case.product.GetAllProductsFromCartUseCase
import com.dexciuq.yummy_express.domain.use_case.product.RemoveAllProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getAllProductsFromCartUseCase: GetAllProductsFromCartUseCase,
    private val removeAllProductFromCartUseCase: RemoveAllProductFromCartUseCase,
    private val makeOrderUseCase: MakeOrderUseCase,
) : ViewModel() {

    val productList get() = getAllProductsFromCartUseCase()

    fun makeOrder(address: Address, productList: List<Product>, total: Long) =
        viewModelScope.launch {
            val order = Order(
                total = total,
                address = address.toString(),
                productList = productList,
            )
            makeOrderUseCase(order)
            clearCart()
        }

    private fun clearCart() = viewModelScope.launch {
        removeAllProductFromCartUseCase()
    }
}