package com.marqumil.bullience_dev.data.network

import com.marqumil.bullience_dev.data.remote.response.ResponseLabelItem
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServiceIndobert {

    @FormUrlEncoded
    @Headers("Authorization: Bearer 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")
    @POST("models/mdhugol/indonesia-bert-sentiment-classification/")
    suspend fun postInputs(
        @Field("inputs") inputs: String
    ): List<List<ResponseLabelItem>>

}