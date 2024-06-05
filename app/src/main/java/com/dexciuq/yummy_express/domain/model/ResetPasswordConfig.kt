package com.dexciuq.yummy_express.domain.model

data class ResetPasswordConfig(
    val code: String,
    val password: String,
)