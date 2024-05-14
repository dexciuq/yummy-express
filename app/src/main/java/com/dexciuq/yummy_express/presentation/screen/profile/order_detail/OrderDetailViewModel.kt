package com.dexciuq.yummy_express.presentation.screen.profile.order_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Order
import com.dexciuq.yummy_express.domain.use_case.order.GetOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val getOrderUseCase: GetOrderUseCase
) : ViewModel() {

    private val _order = MutableStateFlow<Resource<Order>>(Resource.Loading)
    val order get() = _order.asStateFlow()

    fun getOrder(id: Long) = viewModelScope.launch {
        getOrderUseCase(id).collectLatest {
            _order.emit(it)
        }
    }
}