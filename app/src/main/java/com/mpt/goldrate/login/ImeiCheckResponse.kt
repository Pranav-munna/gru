package com.mpt.goldrate.login

import com.google.gson.annotations.SerializedName

/**
 * Created by Pranav Ashok on 24-05-2022.
 */
class ImeiCheckResponse (@SerializedName("isSuccess") val isSuccess: Boolean,
                         @SerializedName("hasError") val hasError: Boolean,
                         @SerializedName("message") val message: String)