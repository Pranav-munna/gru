package com.mpt.goldrate.utils

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast

/**
 * Created by Pranav Ashok on 25-05-2022.
 */
@SuppressLint("StaticFieldLeak")
object Utility {

    private var progressBar: ProgressBar? = null

    fun Context.isInternetAvailable(): Boolean {
        try {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetwork ?: return false
            val activeNetwork = cm.getNetworkCapabilities(netInfo) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> {
                    toast("Internet not available. Please try again!!")
                    Log.e("network flag", "false")
                    false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun Context.toast(message: String?) {

        try {
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun Context.showProgressBar() {
        try {
            Log.e("progress bar", "on")
            val layout =
                (this as Activity).findViewById<View>(R.id.content).rootView as? ViewGroup


            progressBar = ProgressBar(this, null, R.attr.progressBarStyleLarge)
            progressBar!!.isIndeterminate = true

            progressBar?.let { it1 ->
                it1.isIndeterminate = true

                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )

                val rl = RelativeLayout(this)

                rl.gravity = Gravity.CENTER
                rl.addView(it1)

                layout?.addView(rl, params)

                it1.visibility = View.VISIBLE
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgressBar() {
        try {
            progressBar?.let {
                it.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("HardwareIds")
    fun Context.getDeviceId(): String? {
        var imei: String? = ""
        try {
            val telephonyManager =
                this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            imei = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyManager.imei
            } else {
                telephonyManager.deviceId
            }
            if (imei == null || TextUtils.isEmpty(imei) || imei == "") {
                imei =
                    Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
            }
            
        } catch (e: java.lang.Exception) {
            imei = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
            e.printStackTrace()
        }
//test imei
        imei = "353107094891732"
//        imei = "867344037861477"
//        imei = "867344038161471"
//        imei = "6e5fdc439d62d418"
        Log.e("imei", imei!!)
        return imei
    }

}