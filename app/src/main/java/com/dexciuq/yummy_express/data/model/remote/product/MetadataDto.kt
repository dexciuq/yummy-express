package com.dexciuq.yummy_express.data.model.remote.product


import com.google.gson.annotations.SerializedName

data class MetadataDto(
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("page_size")
    val pageSize: Int?,
    @SerializedName("first_page")
    val firstPage: Int?,
    @SerializedName("last_page")
    val lastPage: Int?,
    @SerializedName("total_records")
    val totalRecords: Int?
)