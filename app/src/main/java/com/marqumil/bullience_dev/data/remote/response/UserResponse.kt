package com.marqumil.bullience_dev.data.remote.response

import android.media.session.MediaSession
import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("iat")
	val iat: Int? = null,

	@field:SerializedName("exp")
	val exp: Int? = null,
)

