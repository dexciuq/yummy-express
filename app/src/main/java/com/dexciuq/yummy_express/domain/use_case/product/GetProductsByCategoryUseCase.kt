package com.dexciuq.yummy_express.domain.use_case.product

import com.dexciuq.yummy_express.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke(category: Long) = withContext(Dispatchers.IO) {
        productRepository.getProductsByCategory(category)
    }
}