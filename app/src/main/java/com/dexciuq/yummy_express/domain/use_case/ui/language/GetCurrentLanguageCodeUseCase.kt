package com.dexciuq.yummy_express.domain.use_case.ui.language

import com.dexciuq.yummy_express.domain.repository.UIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentLanguageCodeUseCase @Inject constructor(
    private val uiRepository: UIRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        uiRepository.getCurrentLanguageCode()
    }
}