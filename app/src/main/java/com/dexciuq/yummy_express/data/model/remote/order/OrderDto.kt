package com.dexciuq.yummy_express.data.model.remote.order

import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("total")
    val total: Long?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("delivered_at")
    val deliveredAt: String? = "",
    @SerializedName("status_id")
    val status: String? = "",
    @SerializedName("status_name")
    val statusName: String? = "",
    @SerializedName("status_description")
    val statusDescription: String? = "",
)
