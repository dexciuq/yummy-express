package com.dexciuq.yummy_express.domain.use_case.product

import com.dexciuq.yummy_express.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateProductAmountUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Long, amount: Int) = withContext(Dispatchers.IO) {
        repository.updateProductAmount(id, amount)
    }
}