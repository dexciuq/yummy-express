package com.dexciuq.yummy_express.data.model.remote.auth


import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("firstname")
    val firstname: String? = null,
    @SerializedName("lastname")
    val lastname: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("phone_number")
    val phoneNumber: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("role_id")
    val roleId: Int = 1,
)