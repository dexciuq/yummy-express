package com.dexciuq.yummy_express.data.model.remote

data class ProductDto(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val category: CategoryDto? = null,
    val upc: String? = null,
    val price: Long? = null,
    val discountPercentage: Int? = null,
    val quantity: Long? = null,
    val unit: String? = null,
    val priceUnit: Double? = null,
    val imageURL: String? = null,
    val country: String? = null,
    val brand: String? = null,
)
