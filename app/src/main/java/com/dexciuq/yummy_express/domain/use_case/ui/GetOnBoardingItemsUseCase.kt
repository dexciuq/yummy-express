package com.dexciuq.yummy_express.domain.use_case.ui

import com.dexciuq.yummy_express.domain.repository.UIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetOnBoardingItemsUseCase @Inject constructor(
    private val uiRepository: UIRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        uiRepository.getOnBoardingItems()
    }
}