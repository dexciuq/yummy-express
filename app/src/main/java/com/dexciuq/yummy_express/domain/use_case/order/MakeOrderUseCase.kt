package com.dexciuq.yummy_express.domain.use_case.order

import com.dexciuq.yummy_express.domain.model.Order
import com.dexciuq.yummy_express.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MakeOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(order: Order) = withContext(Dispatchers.IO) {
        orderRepository.makeOrder(order)
    }
}