package com.mpt.goldrate.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GoldRateUpdateResponse {
    @SerializedName("isSuccess")
    @Expose
    var isSuccess: Boolean? = null

    @SerializedName("hasError")
    @Expose
    var hasError: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}