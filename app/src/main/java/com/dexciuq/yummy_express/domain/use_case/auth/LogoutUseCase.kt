package com.dexciuq.yummy_express.domain.use_case.auth

import com.dexciuq.yummy_express.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(accessToken: String) = withContext(Dispatchers.IO) {
        repository.logout(accessToken)
    }
}