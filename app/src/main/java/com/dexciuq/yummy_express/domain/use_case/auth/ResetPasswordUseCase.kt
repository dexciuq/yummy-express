package com.dexciuq.yummy_express.domain.use_case.auth

import com.dexciuq.yummy_express.domain.model.ResetPasswordConfig
import com.dexciuq.yummy_express.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(resetPasswordConfig: ResetPasswordConfig) =
        withContext(Dispatchers.IO) {
            repository.resetPassword(resetPasswordConfig)
        }
}