package com.dexciuq.yummy_express.domain.repository

import com.dexciuq.yummy_express.domain.model.AccessToken
import com.dexciuq.yummy_express.domain.model.AuthTokens
import com.dexciuq.yummy_express.domain.model.User

interface AuthRepository {
    suspend fun getAccessToken(): String
    suspend fun setAccessToken(accessToken: String)
    suspend fun getRefreshToken(): String
    suspend fun setRefreshToken(refreshToken: String)
    suspend fun getAuthSkip(): Boolean
    suspend fun setAuthSkip(skip: Boolean)
    suspend fun login(email: String, password: String): AuthTokens?
    suspend fun refresh(refreshToken: String): AccessToken
    suspend fun logout(accessToken: String)
    suspend fun getProfileInfo(accessToken: String): User
    suspend fun register(
        name: String,
        surname: String,
        email: String,
        phoneNumber: String,
        password: String
    )
}