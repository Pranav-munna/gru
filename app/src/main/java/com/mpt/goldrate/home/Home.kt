package com.mpt.goldrate.home

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.mpt.goldrate.R
import com.mpt.goldrate.login.Login
import com.mpt.goldrate.utils.DecimalDigitsInputFilter
import com.mpt.goldrate.utils.Utility.getDeviceId
import com.mpt.goldrate.utils.Utility.hideProgressBar
import com.mpt.goldrate.utils.Utility.showProgressBar
import com.mpt.goldrate.utils.Utility.toast
import kotlin.system.exitProcess


class Home : AppCompatActivity(), View.OnClickListener {
    private lateinit var cvExit: CardView
    private lateinit var cvUpdate: CardView
    private lateinit var txtVwName: TextView
    private lateinit var btnSave: AppCompatButton
    private lateinit var txtVwAglocNonSolidAmount: EditText
    private lateinit var txtVwAglocSolidAmount: EditText
    private lateinit var dialog: AlertDialog
    private var doubleBackToExitPressedOnce = false
    private var homeViewModel: HomeViewModel? = null

    private var token = ""
    private var empNo = ""
    private var imei = ""
    private var name = ""
    var exitFlag = false

    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        cvExit = findViewById(R.id.cvExit)
        cvExit.setOnClickListener(this)
        cvUpdate = findViewById(R.id.cvUpdate)
        cvUpdate.setOnClickListener(this)
        txtVwName = findViewById(R.id.txtVwName)

        builder = AlertDialog.Builder(this)
        builder.setCancelable(false)

        txtVwName.text = intent.getStringExtra("NAME")
        this.toast("Welcome " + intent.getStringExtra("NAME"))
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        token = intent.getStringExtra("TOKEN")!!
        empNo = intent.getStringExtra("MPNO")!!
        name = intent.getStringExtra("NAME")!!
        imei = this.getDeviceId()!!

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.cvUpdate -> {
                val alertCustomDialog: View =
                    LayoutInflater.from(this).inflate(R.layout.popup_rate_update, null)
                val alert: AlertDialog.Builder = AlertDialog.Builder(this)
                btnSave = alertCustomDialog.findViewById(R.id.btnSave)
                txtVwAglocNonSolidAmount =
                    alertCustomDialog.findViewById(R.id.txtVwAglocNonSolidAmount)
                txtVwAglocSolidAmount = alertCustomDialog.findViewById(R.id.txtVwAglocSolidAmount)
                alert.setView(alertCustomDialog)
                dialog = alert.create()
                dialog.setCancelable(true)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

                btnSave.setOnClickListener {
                    save()
                }

                txtVwAglocNonSolidAmount.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter())
                txtVwAglocSolidAmount.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter())

            }
            R.id.cvExit -> {
                this.doubleBackToExitPressedOnce = true
                onBackPressed()
            }
        }
    }

    private fun updateGoldRateApiCall() {
        homeViewModel!!.updateGoldRate(
            token,
            empNo,
            txtVwAglocSolidAmount.text.trim().toString(),
            txtVwAglocNonSolidAmount.text.trim().toString(),
            imei
        ).observe(
            this
        ) { t ->
            if (t != null) {
                hideProgressBar()
                goldRateUpdateResponse(t)
                Log.e("count", t.message.toString())
            }
        }

    }

    private fun save() {
        Log.e("amount1", txtVwAglocSolidAmount.text.trim().toString())
        Log.e("amount2", txtVwAglocNonSolidAmount.text.trim().toString())
        if (txtVwAglocNonSolidAmount.text.trim().isNotEmpty()) {
            if (txtVwAglocSolidAmount.text.trim().isNotEmpty()) {
                dialog.dismiss()
                this.showProgressBar()

                updateGoldRateApiCall()


            } else
                this.toast("Enter the agloc solid amount")
        } else
            this.toast("Enter the agloc non solid amount")

        builder.setPositiveButton("OK") { _, _ ->
            if (exitFlag) {
                startActivity(Intent(this, Login::class.java))
                finish()
            }
            exitFlag = false
        }
    }

    private fun goldRateUpdateResponse(t: GoldRateUpdateResponse) {
        if (t.isSuccess == true && t.hasError == false) {
            this.toast(t.message)
        } else {
            if (t.isSuccess == false && t.hasError == false) {
                builder.setTitle(getString(R.string.app_name))
                builder.setMessage(t.message)
            }
            exitFlag = false
            builder.show()
        }


    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            exitProcess(-1)
        }

        this.doubleBackToExitPressedOnce = true
        this.toast("Please click BACK again to exit")
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)

    }
}