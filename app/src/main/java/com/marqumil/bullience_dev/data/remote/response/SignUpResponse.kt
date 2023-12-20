package com.marqumil.bullience_dev.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(

	@field:SerializedName("msg")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
