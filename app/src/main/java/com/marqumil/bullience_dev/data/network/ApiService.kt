package com.marqumil.bullience_dev.data.network

import com.marqumil.bullience_dev.data.remote.ResponseList
import com.marqumil.bullience_dev.data.remote.ResponseObject
import com.marqumil.bullience_dev.data.remote.post.LoginBody
import com.marqumil.bullience_dev.data.remote.post.SignUpBody
import com.marqumil.bullience_dev.data.remote.response.LoginResponse
import com.marqumil.bullience_dev.data.remote.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.*

interface ApiService {

    @POST("/user/login")
    suspend fun postLogin(
        @Body loginBody: LoginBody
    ): LoginResponse

    @GET("/user/getUsers")
    suspend fun getUser(
        @Query("email") email: String
    ): ResponseObject<LoginResponse>

    @GET("/user/getUsers")
    suspend fun getAllUser(
        @Query("email") email: String
    ): ResponseList<LoginResponse>

    @POST("/user/register")
    suspend fun postRegister(
        @Body signUpBody: SignUpBody
    ): SignUpResponse


}