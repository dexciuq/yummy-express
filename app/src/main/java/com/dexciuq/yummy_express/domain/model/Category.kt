package com.dexciuq.yummy_express.domain.model

data class Category(
    val id: Long,
    val name: String,
    val imageURL: String,
    val description: String,
)