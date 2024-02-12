package com.dexciuq.yummy_express.data.model.remote.product


import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Long?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("upc")
    val upc: String?,
    @SerializedName("quantity")
    val quantity: Long?,
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("step")
    val step: Double?,
    @SerializedName("category_id")
    val categoryId: Long?,
    @SerializedName("category_name")
    val categoryName: String?,
    @SerializedName("category_description")
    val categoryDescription: String?,
    @SerializedName("category_image")
    val categoryImage: String?,
    @SerializedName("discount_id")
    val discountId: Long?,
    @SerializedName("discount_name")
    val discountName: String?,
    @SerializedName("discount_description")
    val discountDescription: String?,
    @SerializedName("discount_percent")
    val discountPercent: Int?,
    @SerializedName("discount_created_at")
    val discountCreatedAt: String?,
    @SerializedName("discount_started_at")
    val discountStartedAt: String?,
    @SerializedName("discount_ended_at")
    val discountEndedAt: String?,
    @SerializedName("unit_id")
    val unitId: Long?,
    @SerializedName("unit_name")
    val unitName: String?,
    @SerializedName("unit_description")
    val unitDescription: String?,
    @SerializedName("brand_id")
    val brandId: Long?,
    @SerializedName("brand_name")
    val brandName: String?,
    @SerializedName("brand_description")
    val brandDescription: String?,
    @SerializedName("country_id")
    val countryId: Long?,
    @SerializedName("country_name")
    val countryName: String?,
    @SerializedName("country_description")
    val countryDescription: String?,
    @SerializedName("alpha2")
    val alpha2: String?,
    @SerializedName("alpha3")
    val alpha3: String?
)