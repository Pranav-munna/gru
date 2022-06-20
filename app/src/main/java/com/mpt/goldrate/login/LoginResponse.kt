package com.mpt.goldrate.login

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("hasError") val hasError: Boolean,
    @SerializedName("empName") val empName: String,
    @SerializedName("message") val message: String,
    @SerializedName("token") val token: String
)
