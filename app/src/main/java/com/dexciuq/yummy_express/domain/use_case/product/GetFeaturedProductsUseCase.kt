package com.dexciuq.yummy_express.domain.use_case.product

import com.dexciuq.yummy_express.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFeaturedProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        productRepository.getFeaturedProducts()
    }
}