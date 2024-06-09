package com.dexciuq.yummy_express.data.repository

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.domain.model.Order
import com.dexciuq.yummy_express.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remote: DataSource.Remote,
) : OrderRepository {
    override suspend fun makeOrder(order: Order) =
        remote.makeOrder(order)

    override suspend fun getOrderById(id: Long): Flow<Resource<Order>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getOrderById(id)
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun getOrderList(): Flow<Resource<List<Order>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getOrderList().sortedByDescending { it.id }
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }
}