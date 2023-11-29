package com.dexciuq.yummy_express.domain.use_case.product

import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveProductFromCartUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) = withContext(Dispatchers.IO) {
        repository.removeProductFromCart(product)
    }
}