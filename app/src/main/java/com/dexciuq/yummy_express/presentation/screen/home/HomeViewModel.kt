package com.dexciuq.yummy_express.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.use_case.ui.GetAllBannersUseCase
import com.dexciuq.yummy_express.domain.use_case.product.GetFeaturedProductsUseCase
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.use_case.category.GetCategoriesUseCase
import com.dexciuq.yummy_express.domain.use_case.category.GetHomeCategoriesUseCase
import com.dexciuq.yummy_express.domain.use_case.product.AddProductToCartUseCase
import com.dexciuq.yummy_express.domain.use_case.product.RemoveProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeaturedProductsUseCase: GetFeaturedProductsUseCase,
    private val getAllBannersUseCase: GetAllBannersUseCase,
    private val getHomeCategoriesUseCase: GetHomeCategoriesUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
) : ViewModel() {

    private val _featuredProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Loading)
    val featuredProducts get() = _featuredProducts.asStateFlow()

    private val _banners = MutableStateFlow<Resource<List<Banner>>>(Resource.Loading)
    val banners get() = _banners.asStateFlow()

    private val _categories = MutableStateFlow<Resource<List<Category>>>(Resource.Loading)
    val categories get() = _categories.asStateFlow()

    init {
        getBanners()
        getCategories()
        getFeaturedProducts()
    }

    private fun getCategories() = viewModelScope.launch {
        getHomeCategoriesUseCase().collectLatest {
            _categories.emit(it)
        }
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