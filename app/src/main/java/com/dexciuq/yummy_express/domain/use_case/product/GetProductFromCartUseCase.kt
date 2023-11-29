package com.dexciuq.yummy_express.domain.use_case.product

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductFromCartUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Long): Flow<Resource<Product>> =
        repository.getCartProductById(id)
}