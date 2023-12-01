package com.dexciuq.yummy_express.presentation.screen.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.domain.use_case.product.GetAllProductsFromCartUseCase
import com.dexciuq.yummy_express.domain.use_case.product.RemoveAllProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getAllProductsFromCartUseCase: GetAllProductsFromCartUseCase,
    private val removeAllProductFromCartUseCase: RemoveAllProductFromCartUseCase
) : ViewModel() {

    val productList get() = getAllProductsFromCartUseCase()

    fun clearCart() = viewModelScope.launch {
        removeAllProductFromCartUseCase()
    }
}