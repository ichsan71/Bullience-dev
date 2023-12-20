package com.marqumil.bullience_dev.data.remote.response

import android.media.session.MediaSession
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class LoginResponse(

	@field:SerializedName("msg")
	val message: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)

