package com.marqumil.bullience_dev.data.remote

import com.google.gson.annotations.SerializedName

data class ResponseList<T>(

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("data")
    val data: List<T>?,

    @field:SerializedName("result")
    val result: String? = null
)