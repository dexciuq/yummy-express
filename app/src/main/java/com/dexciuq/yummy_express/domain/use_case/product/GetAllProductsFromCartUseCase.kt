package com.dexciuq.yummy_express.domain.use_case.product

import com.dexciuq.yummy_express.domain.repository.ProductRepository
import javax.inject.Inject

class GetAllProductsFromCartUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke() = repository.getAllProductsFromCart()
}