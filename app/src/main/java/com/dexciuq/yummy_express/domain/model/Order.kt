package com.dexciuq.yummy_express.domain.model

data class Order(
    val id: Long,
    val userId: Long = 1,
    val total: Long = 165654,
    val address: String = "Kabanbay batyr 60/6A",
    val status: String = "Confirmed",
    val courier: String = "Nurdaulet",
    val createdAt: String = "07 December 2023",
    val deliveredAt: String = "07 December 2023",
)
