package com.mpt.goldrate.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


/**
 * Created by Pranav Ashok on 23-05-2022.
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val loginRepository: LoginRepository

    fun checkImei(imei: String): LiveData<ImeiCheckResponse> {
        return loginRepository.getMutableLiveData(imei)
    }

    fun login(imei: String,empId: String, password: String): LiveData<LoginResponse> {
        return loginRepository.getMutableLiveDataLogin(imei,empId,password)
    }

    init {
        loginRepository = LoginRepository(application)
    }
}