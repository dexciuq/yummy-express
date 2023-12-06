package com.dexciuq.yummy_express.domain.use_case.auth

import com.dexciuq.yummy_express.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        surname: String,
        email: String,
        phoneNumber: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        repository.register(name, surname, email, phoneNumber, password)
    }
}