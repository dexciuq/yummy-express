package com.dexciuq.yummy_express.presentation.screen.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.use_case.auth.GetAuthSkipUseCase
import com.dexciuq.yummy_express.domain.use_case.product.AddProductToCartUseCase
import com.dexciuq.yummy_express.domain.use_case.product.GetAllProductsFromCartUseCase
import com.dexciuq.yummy_express.domain.use_case.product.RemoveProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllProductsFromCartUseCase: GetAllProductsFromCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val getAuthSkipUseCase: GetAuthSkipUseCase,
) : ViewModel() {

    private val _enabledButton = MutableLiveData<Boolean>()
    val enabledButton: LiveData<Boolean> = _enabledButton

    val cartProductList get() = getAllProductsFromCartUseCase()

    fun removeProductFromCart(product: Product) = viewModelScope.launch {
        removeProductFromCartUseCase(product)
    }

    fun updateAmount(product: Product) = viewModelScope.launch {
        addProductToCartUseCase(product)
    }

    fun isButtonEnabled() = viewModelScope.launch {
        val authSkip = getAuthSkipUseCase()
        _enabledButton.postValue(authSkip)
    }
}