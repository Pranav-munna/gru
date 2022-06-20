package com.mpt.goldrate.network

import com.mpt.goldrate.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Pranav Ashok on 26-05-2022.
 */

object RetrofitInstance {
    private var retrofit: Retrofit? = null
    fun getApiService(): GRUInterphase {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(GRUInterphase::class.java)
    }
}