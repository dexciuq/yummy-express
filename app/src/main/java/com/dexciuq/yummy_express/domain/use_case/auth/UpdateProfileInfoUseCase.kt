package com.dexciuq.yummy_express.domain.use_case.auth

import com.dexciuq.yummy_express.domain.model.User
import com.dexciuq.yummy_express.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateProfileInfoUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User) = withContext(Dispatchers.IO) {
        repository.updateUserProfile(user)
    }
}