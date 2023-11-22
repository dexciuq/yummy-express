package com.dexciuq.yummy_express.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Long,
    val name: String,
    val imageURL: String,
    val description: String,
) : Parcelable