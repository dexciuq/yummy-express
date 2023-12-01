package com.dexciuq.yummy_express.domain.use_case.ui.theme

import com.dexciuq.yummy_express.domain.repository.UIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetDarkModeUseCase @Inject constructor(
    private val uiRepository: UIRepository
) {
    suspend operator fun invoke(isDarkMode: Boolean) = withContext(Dispatchers.IO) {
        uiRepository.setDarkMode(isDarkMode)
    }
}
