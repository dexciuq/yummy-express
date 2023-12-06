package com.dexciuq.yummy_express.domain.model

data class User(
    val id: Long,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String
)