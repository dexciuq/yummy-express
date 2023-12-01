package com.dexciuq.yummy_express.domain.use_case.ui.language

import com.dexciuq.yummy_express.domain.repository.UIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetCurrentLanguageCodeUseCase @Inject constructor(
    private val uiRepository: UIRepository
) {
    suspend operator fun invoke(languageCode: String) = withContext(Dispatchers.IO) {
        uiRepository.setCurrentLanguageCode(languageCode)
    }
}