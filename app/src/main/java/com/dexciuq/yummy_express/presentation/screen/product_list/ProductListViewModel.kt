package com.dexciuq.yummy_express.presentation.screen.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.use_case.product.AddProductToCartUseCase
import com.dexciuq.yummy_express.domain.use_case.product.GetAllProductsFromCartUseCase
import com.dexciuq.yummy_express.domain.use_case.product.GetProductsByCategoryUseCase
import com.dexciuq.yummy_express.domain.use_case.product.RemoveProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
) : ViewModel() {

    private val _products = MutableStateFlow<Resource<List<Product>>>(Resource.Loading)
    val products get() = _products.asStateFlow()

    fun getProductListByCategory(categoryId: Long) = viewModelScope.launch {
        getProductsByCategoryUseCase(categoryId).collectLatest {
            _products.emit(it)
        }
    }

    fun addProductToCart(product: Product) = viewModelScope.launch {
        addProductToCartUseCase(product)
    }

    fun removeProductFromCart(product: Product) = viewModelScope.launch {
        removeProductFromCartUseCase(product)
    }

    fun updateAmount(product: Product) = viewModelScope.launch {
        addProductToCartUseCase(product)
    }
}