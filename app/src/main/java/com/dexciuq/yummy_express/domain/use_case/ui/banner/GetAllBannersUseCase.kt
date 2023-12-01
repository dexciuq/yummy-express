package com.dexciuq.yummy_express.domain.use_case.ui.banner

import com.dexciuq.yummy_express.domain.repository.UIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllBannersUseCase @Inject constructor(
    private val uiRepository: UIRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        uiRepository.getBanners()
    }
}