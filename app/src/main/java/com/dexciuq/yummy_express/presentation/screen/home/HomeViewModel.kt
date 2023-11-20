package com.dexciuq.yummy_express.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.use_case.banner.GetAllBannersUseCase
import com.dexciuq.yummy_express.domain.use_case.product.GetFeaturedProductsUseCase
import com.dexciuq.yummy_express.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeaturedProductsUseCase: GetFeaturedProductsUseCase,
    private val getAllBannersUseCase: GetAllBannersUseCase,
) : ViewModel() {

    private val _featuredProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Loading)
    val featuredProducts get() = _featuredProducts.asStateFlow()

    private val _banners = MutableStateFlow<Resource<List<Banner>>>(Resource.Loading)
    val banners get() = _banners.asStateFlow()

    init {
        getBanners()
        getFeaturedProducts()
    }

    private fun getFeaturedProducts() = viewModelScope.launch {
        getFeaturedProductsUseCase().collectLatest {
            _featuredProducts.emit(it)
        }
    }

    private fun getBanners() = viewModelScope.launch {
        getAllBannersUseCase().collectLatest {
            _banners.emit(it)
        }
    }
}