package com.dexciuq.yummy_express.data.mapper

import com.dexciuq.yummy_express.data.model.local.ProductEntity
import com.dexciuq.yummy_express.data.model.remote.CategoryDto
import com.dexciuq.yummy_express.data.model.remote.LoginResponse
import com.dexciuq.yummy_express.data.model.remote.ProductDto
import com.dexciuq.yummy_express.data.model.remote.RefreshResponse
import com.dexciuq.yummy_express.data.model.remote.UserDto
import com.dexciuq.yummy_express.data.model.remote.UserResponse
import com.dexciuq.yummy_express.domain.model.AccessToken
import com.dexciuq.yummy_express.domain.model.AuthTokens
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.model.User

fun ProductDto.fromDtoToProduct() = Product(
    id = id ?: 0,
    name = name.orEmpty(),
    description = description.orEmpty(),
    category = Category(
        id = categoryId ?: 0,
        name = categoryName.orEmpty(),
        imageURL = categoryImage.orEmpty(),
        description = categoryDescription.orEmpty()
    ),
    upc = upc.orEmpty(),
    price = price ?: 0,
    discountPercentage = discountPercent ?: 0,
    quantity = quantity ?: 0,
    unit = unitName.orEmpty(),
    priceUnit = step ?: 0.0,
    imageURL = image.orEmpty(),
    country = countryName.orEmpty(),
    brand = brandName.orEmpty(),
    amount = null
)

fun ProductEntity.fromEntityToProduct() = Product(
    id = id,
    name = name,
    description = description,
    category = Category(
        id = category.id,
        name = category.name,
        imageURL = category.imageURL,
        description = category.description
    ),
    upc = upc,
    price = price,
    discountPercentage = discountPercentage,
    quantity = quantity,
    unit = unit,
    priceUnit = priceUnit,
    imageURL = imageURL.orEmpty(),
    country = country,
    brand = brand,
    amount = amount
)

fun Product.toProductEntity() = ProductEntity(
    id = id,
    name = name,
    description = description,
    category = Category(
        id = category.id,
        name = category.name,
        imageURL = category.imageURL,
        description = category.description
    ),
    upc = upc,
    price = price,
    discountPercentage = discountPercentage,
    quantity = quantity,
    unit = unit,
    priceUnit = priceUnit,
    imageURL = imageURL.orEmpty(),
    country = country,
    brand = brand,
    amount = amount ?: priceUnit
)

fun CategoryDto.toCategory() = Category(
    id = id ?: 0,
    name = name.orEmpty(),
    imageURL = image.orEmpty(),
    description = description.orEmpty(),
)

fun LoginResponse.toAuthTokens() = AuthTokens(
    accessToken = accessToken,
    refreshToken = refreshToken,
)

fun RefreshResponse.toAccessToken() = AccessToken(
    accessToken = accessToken,
)

fun UserDto.toUser() = User(
    id = id,
    name = firstname,
    surname = lastname,
    email = email,
    phoneNumber = phoneNumber
)

fun List<ProductEntity>.fromEntityToProduct() = map(ProductEntity::fromEntityToProduct)

fun List<ProductDto>.fromDtoToProduct() = map(ProductDto::fromDtoToProduct)

fun List<CategoryDto>.toCategory() = map(CategoryDto::toCategory)
