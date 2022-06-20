package com.mpt.goldrate.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val homeRepository: HomeRepository

    fun updateGoldRate(
        token: String,
        EMPNO: String,
        aglocSolidAmount: String,
        aglocNonSolidAmount: String,
        IMEI: String
    ): LiveData<GoldRateUpdateResponse> {
        return homeRepository.getMutableLiveDataUpdateGoldRate(
            token,
            EMPNO,
            aglocSolidAmount,
            aglocNonSolidAmount,
            IMEI
        )
    }

    init {
        homeRepository = HomeRepository(application)
    }

}