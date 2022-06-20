package com.mpt.goldrate.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mpt.goldrate.R
import com.mpt.goldrate.login.Login
import com.mpt.goldrate.utils.Utility.hideProgressBar
import com.mpt.goldrate.utils.Utility.isInternetAvailable
import com.mpt.goldrate.utils.Utility.toast
import com.mpt.goldrate.utils.Utility.showProgressBar

class CustomSplashScreen : AppCompatActivity() {
    private lateinit var splashViewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        this.showProgressBar()
        if (this.isInternetAvailable()) {
            hideProgressBar()
            initViewModel()
            observeSplashLiveData()
        } else {
            this.toast("Internet not available. Please try again!!")
        }

    }

    private fun initViewModel() {
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    private fun observeSplashLiveData() {
        splashViewModel.initSplashScreen()
        val observer = Observer<SplashModel> {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        splashViewModel.liveData.observe(this, observer)
    }

}