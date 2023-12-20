package com.marqumil.bullience_dev.data.remote

import android.media.session.MediaSession
import com.google.gson.annotations.SerializedName

data class ResponseObject<T>(
    @field:SerializedName("data")
    val data: T?,

    @field:SerializedName("msg")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("error")
    val error: String? = null,

    @field:SerializedName("token")
    val token: MediaSession.Token? = null,

    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("user")
    val user: T?
)
