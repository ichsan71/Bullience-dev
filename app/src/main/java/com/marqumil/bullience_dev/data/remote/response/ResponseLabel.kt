package com.marqumil.bullience_dev.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseLabelOne(

	@field:SerializedName("ResponseLabel")
	val responseLabel: List<ResponseLabelItem>
)

data class ResponseLabelItem(

	@field:SerializedName("score")
	val score: Any,

	@field:SerializedName("label")
	val label: String
)
