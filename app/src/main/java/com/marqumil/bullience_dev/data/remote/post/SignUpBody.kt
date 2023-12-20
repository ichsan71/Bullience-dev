package com.marqumil.bullience_dev.data.remote.post

import com.google.gson.annotations.SerializedName

data class SignUpBody (

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("confirmasiPassword")
    val confirmasiPassword: String? = null,


)
