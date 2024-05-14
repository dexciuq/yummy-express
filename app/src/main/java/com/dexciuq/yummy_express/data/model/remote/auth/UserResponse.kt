package com.dexciuq.yummy_express.data.model.remote.auth


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("user")
    val userDto: UserDto
)