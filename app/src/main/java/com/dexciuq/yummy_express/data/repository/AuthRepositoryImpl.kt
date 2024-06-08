package com.dexciuq.yummy_express.data.repository

import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.domain.model.ResetPasswordConfig
import com.dexciuq.yummy_express.domain.model.User
import com.dexciuq.yummy_express.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: DataSource.Remote,
    private val preference: DataSource.Preference
) : AuthRepository {
    override suspend fun getAccessToken(): String =
        preference.getAccessToken()

    override suspend fun setAccessToken(accessToken: String) =
        preference.setAccessToken(accessToken)

    override suspend fun getRefreshToken(): String =
        preference.getRefreshToken()

    override suspend fun setRefreshToken(refreshToken: String) =
        preference.setRefreshToken(refreshToken)

    override suspend fun getAuthSkip(): Boolean =
        preference.getAuthSkip()

    override suspend fun setAuthSkip(skip: Boolean) =
        preference.setAuthSkip(skip)

    override suspend fun login(email: String, password: String) =
        remote.login(email, password)

    override suspend fun refresh(refreshToken: String) =
        remote.refresh(refreshToken)

    override suspend fun logout(accessToken: String) =
        remote.logout(accessToken)

    override suspend fun getProfileInfo(accessToken: String): User =
        remote.getProfileInfo(accessToken)

    override suspend fun updateUserProfile(user: User): User =
        remote.updateUserProfile(user)

    override suspend fun register(
        name: String,
        surname: String,
        email: String,
        phoneNumber: String,
        password: String
    ) = remote.register(name, surname, email, phoneNumber, password)

    override suspend fun sendCode(email: String): Boolean =
        remote.sendCode(email)

    override suspend fun verifyCode(code: String): Boolean =
        remote.verifyCode(code)

    override suspend fun resetPassword(resetPasswordConfig: ResetPasswordConfig): Boolean =
        remote.resetPassword(resetPasswordConfig)
}