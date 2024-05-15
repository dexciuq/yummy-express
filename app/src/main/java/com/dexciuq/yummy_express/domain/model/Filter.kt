package com.dexciuq.yummy_express.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val name: String? = null,
    val category: Category? = null,
    val brand: List<Long>? = null,
    val page: Int? = null,
    val pageSize: Int? = null,
    var sort: String? = null,
) : Parcelable
