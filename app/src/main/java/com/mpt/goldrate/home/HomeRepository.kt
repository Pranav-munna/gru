package com.mpt.goldrate.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mpt.goldrate.BuildConfig
import com.mpt.goldrate.network.GRUInterphase
import com.mpt.goldrate.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeRepository(application: Application) {
    private val application: Application

    fun getMutableLiveDataUpdateGoldRate(
        token: String,
        EMPNO: String,
        aglocSolidAmount: String,
        aglocNonSolidAmount: String,
        IMEI: String
    ): MutableLiveData<GoldRateUpdateResponse> {
        val mutableLiveDataUpdateGoldRate: MutableLiveData<GoldRateUpdateResponse> =
            MutableLiveData<GoldRateUpdateResponse>()
        val apiService: GRUInterphase = RetrofitInstance.getApiService()
        val call: Call<GoldRateUpdateResponse> =
            apiService.updateRate(
                token, BuildConfig.JSON, EMPNO, aglocSolidAmount, aglocNonSolidAmount,
                IMEI
            )
        call.enqueue(object : Callback<GoldRateUpdateResponse?> {
            override fun onResponse(
                call: Call<GoldRateUpdateResponse?>,
                response: Response<GoldRateUpdateResponse?>
            ) {
                val goldRateUpdateResponse: GoldRateUpdateResponse? = response.body()
                if (goldRateUpdateResponse != null) {
                    mutableLiveDataUpdateGoldRate.value = goldRateUpdateResponse
                }
                if (response.code() == 401 || response.code() == 403 || response.code() == 500) {
                    val errorResponse = GoldRateUpdateResponse()
                    errorResponse.isSuccess = false
                    errorResponse.hasError = true
                    when (response.code()) {
                        401 -> {
                            errorResponse.message = "401"
                        }
                        403 -> {
                            errorResponse.message = "403"
                        }
                        500 -> {
                            errorResponse.message = "500"
                        }
                    }
                    mutableLiveDataUpdateGoldRate.value = errorResponse
                }
                val gson = Gson()
                val bodyInStringFormat = gson.toJson(response.body())
                Log.e("response GRUpdate", bodyInStringFormat)
                Log.e("Status Code GRUpdate", response.code().toString())
            }

            override fun onFailure(call: Call<GoldRateUpdateResponse?>, t: Throwable) {
                Log.e("ListSize", " - > Error    " + t.message)
            }
        })
        return mutableLiveDataUpdateGoldRate
    }

    init {
        this.application = application
    }
}