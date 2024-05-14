package com.dexciuq.yummy_express.data.model.remote.auth


import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("role_id")
    val roleId: Int = 1,
)