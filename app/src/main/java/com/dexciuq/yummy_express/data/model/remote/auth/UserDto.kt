package com.dexciuq.yummy_express.data.model.remote.auth


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("role_id")
    val roleId: Int
)