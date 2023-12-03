package com.dexciuq.yummy_express.presentation.screen.profile.my_orders

import androidx.lifecycle.ViewModel
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(

) : ViewModel() {

    private val _orders = MutableStateFlow<Resource<List<Order>>>(Resource.Loading)
    val orders get() = _orders.asStateFlow()

    init {
        getMyOrders()
    }

    private fun getMyOrders() {
        _orders.value = Resource.Success(
            data = listOf(
                Order(id = 546545),
                Order(id = 454122),
                Order(id = 124646)
            )
        )
    }
}