package com.dexciuq.yummy_express.data.model.remote.auth

import com.google.gson.annotations.SerializedName

data class ResetCodeRequest(
    @SerializedName("email")
    val email: String,
)
