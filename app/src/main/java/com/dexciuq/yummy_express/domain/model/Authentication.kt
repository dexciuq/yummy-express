package com.dexciuq.yummy_express.domain.model

data class Authentication(
    val message: String? = null,
    val tokens: AuthTokens? = null
)