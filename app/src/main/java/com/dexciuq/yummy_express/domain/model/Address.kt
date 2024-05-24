package com.dexciuq.yummy_express.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val street: String,
    val apartment: Int,
    val entrance: Int,
    val floor: Int,
) : Parcelable {
    override fun toString(): String {
        return "Address: $street. Apartment: $apartment. Entrance: $entrance. Floor: $floor"
    }
}