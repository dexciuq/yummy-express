package com.dexciuq.yummy_express.domain.model

import androidx.annotation.DrawableRes

data class Banner(
    val id: Long,
    @DrawableRes val image: Int,
)
