package com.dexciuq.yummy_express.presentation.screen.profile.my_orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Order
import com.dexciuq.yummy_express.domain.use_case.order.GetOrderListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    private val getOrderListUseCase: GetOrderListUseCase,
) : ViewModel() {

    private val _orders = MutableStateFlow<Resource<List<Order>>>(Resource.Loading)
    val orders get() = _orders.asStateFlow()

    init {
        getMyOrders()
    }

    private fun getMyOrders() = viewModelScope.launch {
        getOrderListUseCase().collectLatest {
            _orders.emit(it)
        }
    }
}