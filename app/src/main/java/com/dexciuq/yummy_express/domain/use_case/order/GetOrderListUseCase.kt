package com.dexciuq.yummy_express.domain.use_case.order

import com.dexciuq.yummy_express.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetOrderListUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        orderRepository.getOrderList()
    }
}