package com.dexciuq.yummy_express.domain.use_case.ui.on_boarding

import com.dexciuq.yummy_express.domain.repository.UIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetOnBoardingCompleteUseCase @Inject constructor(
    private val uiRepository: UIRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        uiRepository.getOnBoardingComplete()
    }
}