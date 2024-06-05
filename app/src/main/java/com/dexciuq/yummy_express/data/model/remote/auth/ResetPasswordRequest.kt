package com.dexciuq.yummy_express.data.model.remote.auth

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("code")
    val code: String,
    @SerializedName("new_password")
    val newPassword: String,
)
