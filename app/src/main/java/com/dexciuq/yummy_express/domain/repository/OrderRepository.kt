package com.dexciuq.yummy_express.domain.repository

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun makeOrder(order: Order)
    suspend fun getOrderById(id: Long): Flow<Resource<Order>>
    suspend fun getOrderList(): Flow<Resource<List<Order>>>
}