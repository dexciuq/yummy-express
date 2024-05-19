package com.dexciuq.yummy_express.presentation.screen.profile.upc_scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.use_case.product.GetProductByUPCUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UPCScannerViewModel @Inject constructor(
    private val getProductByUPCUseCase: GetProductByUPCUseCase
) : ViewModel() {

    private val _product = MutableStateFlow<Resource<Product>?>(Resource.Loading)
    val product get() = _product.asStateFlow()

    fun getProduct(upc: String) = viewModelScope.launch {
        getProductByUPCUseCase(upc).collectLatest {
            _product.emit(it)
        }
    }

    fun release() = viewModelScope.launch {
        _product.emit(null)
    }
}