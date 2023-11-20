package com.dexciuq.yummy_express.data.mapper

import com.dexciuq.yummy_express.data.model.remote.CategoryDto
import com.dexciuq.yummy_express.data.model.remote.ProductDto
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.common.orPlaceholder

fun ProductDto.toProduct() = Product(
    id = id ?: 0,
    name = name.orEmpty(),
    description = description.orEmpty(),
    category = category?.toCategory() ?: Category(id = 0, name = "", imageURL = "", description = ""),
    upc = upc.orEmpty(),
    price = price ?: 0,
    discountPercentage = discountPercentage ?: 0,
    quantity = quantity ?: 0,
    unit = unit.orEmpty(),
    priceUnit = priceUnit ?: 0.0,
    imageURL = imageURL.orPlaceholder(),
    country = country.orEmpty(),
    brand = brand.orEmpty()
)

fun CategoryDto.toCategory() = Category(
    id = id ?: 0,
    name = name.orEmpty(),
    imageURL = imageURL.orPlaceholder(),
    description = description.orEmpty(),
)
