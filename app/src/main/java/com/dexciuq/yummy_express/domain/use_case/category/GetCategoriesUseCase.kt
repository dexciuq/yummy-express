package com.dexciuq.yummy_express.domain.use_case.category

import com.dexciuq.yummy_express.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        categoryRepository.getCategories()
    }
}