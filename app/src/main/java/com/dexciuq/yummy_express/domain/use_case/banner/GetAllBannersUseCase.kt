package com.dexciuq.yummy_express.domain.use_case.banner

import com.dexciuq.yummy_express.domain.repository.BannerRepository
import com.dexciuq.yummy_express.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllBannersUseCase @Inject constructor(
    private val bannerRepository: BannerRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        bannerRepository.getBanners()
    }
}