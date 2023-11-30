package com.dexciuq.yummy_express.domain.use_case.product

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemCountUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<Int>> =
        repository.getCartItemCount()
}