package com.dexciuq.yummy_express.presentation.screen.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.use_case.product.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _product = MutableStateFlow<Resource<Product>>(Resource.Loading)
    val product get() = _product.asStateFlow()

    fun getProduct(id: Long) = viewModelScope.launch {
        getProductByIdUseCase(id).collectLatest {
            _product.emit(it)
        }
    }
}