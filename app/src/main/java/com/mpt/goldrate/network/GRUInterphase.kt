package com.mpt.goldrate.network

import com.mpt.goldrate.home.GoldRateUpdateResponse
import com.mpt.goldrate.login.ImeiCheckResponse
import com.mpt.goldrate.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by Pranav Ashok on 25-05-2022.
 */
interface GRUInterphase {
    @FormUrlEncoded
    @POST("login/IMEICheck")
    fun imeiCheck(
        @Header("Accept") json: String,
        @Field("IMEI") IMEI: String
    ): Call<ImeiCheckResponse>

    @FormUrlEncoded
    @POST("login/userAuthentication")
    fun login(
        @Header("Accept") json: String,
        @Field("IMEI") IMEI: String,
        @Field("MPNo") MPNo: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("ltv/updateRate")
    fun updateRate(
        @Header("x-access-token") authKey: String,
        @Header("Accept") json: String,
        @Field("MPNo") MPNo: String,
        @Field("aglocSolidAmount") aglocSolidAmount: String,
        @Field("aglocNonSolidAmount") aglocNonSolidAmount: String,
        @Field("IMEI") IMEI: String
    ): Call<GoldRateUpdateResponse>

}