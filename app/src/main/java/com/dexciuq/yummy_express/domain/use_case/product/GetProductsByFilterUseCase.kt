package com.dexciuq.yummy_express.domain.use_case.product

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Filter
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProductsByFilterUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke(filter: Filter): Flow<Resource<List<Product>>> =
        withContext(Dispatchers.IO) {
            productRepository.getProductsByFilter(filter)
        }
}