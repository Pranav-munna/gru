package com.mpt.goldrate.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mpt.goldrate.BuildConfig
import com.mpt.goldrate.network.RetrofitInstance.getApiService
import com.mpt.goldrate.network.GRUInterphase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Pranav Ashok on 25-05-2022.
 */

class LoginRepository(application: Application) {
    private val mutableLiveData: MutableLiveData<ImeiCheckResponse> =
        MutableLiveData<ImeiCheckResponse>()

    private val mutableLiveDataLogin: MutableLiveData<LoginResponse> =
        MutableLiveData<LoginResponse>()

    private val application: Application

    fun getMutableLiveData(imei: String): MutableLiveData<ImeiCheckResponse> {
        val apiService: GRUInterphase = getApiService()
        val call: Call<ImeiCheckResponse> =
            apiService.imeiCheck(BuildConfig.JSON, imei)
        call.enqueue(object : Callback<ImeiCheckResponse?> {
            override fun onResponse(
                call: Call<ImeiCheckResponse?>?,
                response: Response<ImeiCheckResponse?>
            ) {
                val imeiCheckResponse: ImeiCheckResponse? = response.body()
                if (imeiCheckResponse != null) {
                    mutableLiveData.value = imeiCheckResponse
                }
                val gson = Gson()
                val bodyInStringFormat = gson.toJson(imeiCheckResponse)
                Log.e("response imei", bodyInStringFormat)
            }

            override fun onFailure(call: Call<ImeiCheckResponse?>?, t: Throwable) {
                Log.d("ListSize", " - > Error    " + t.message)
            }
        })
        return mutableLiveData
    }

    fun getMutableLiveDataLogin(
        imei: String,
        empId: String,
        password: String,
    ): MutableLiveData<LoginResponse> {
        val apiService: GRUInterphase = getApiService()
        val call: Call<LoginResponse> =
            apiService.login(BuildConfig.JSON, imei, empId, password)
        call.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>?,
                response: Response<LoginResponse?>
            ) {
                val loginResponse: LoginResponse? = response.body()
                if (loginResponse != null) {
                    mutableLiveDataLogin.value = loginResponse
                }
                val gson = Gson()
                val bodyInStringFormat = gson.toJson(loginResponse)
                Log.e("response login", bodyInStringFormat)
            }

            override fun onFailure(call: Call<LoginResponse?>?, t: Throwable) {
                Log.d("ListSize", " - > Error    " + t.message)
            }
        })
        return mutableLiveDataLogin
    }

    init {
        this.application = application
    }
}