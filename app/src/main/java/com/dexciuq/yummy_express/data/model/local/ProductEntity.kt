package com.dexciuq.yummy_express.data.model.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dexciuq.yummy_express.domain.model.Category

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    @Embedded(prefix = "category_")
    val category: Category,
    val upc: String,
    val price: Long,
    val discountPercentage: Int,
    val quantity: Long,
    val unit: String,
    val priceUnit: Double,
    val imageURL: String,
    val country: String,
    val brand: String,
    val amount: Double,
)
