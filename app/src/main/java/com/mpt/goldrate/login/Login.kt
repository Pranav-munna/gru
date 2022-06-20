package com.mpt.goldrate.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.mpt.goldrate.R
import com.mpt.goldrate.home.Home
import com.mpt.goldrate.utils.Utility.getDeviceId
import com.mpt.goldrate.utils.Utility.hideProgressBar
import com.mpt.goldrate.utils.Utility.showProgressBar
import com.mpt.goldrate.utils.Utility.toast


class Login : AppCompatActivity(R.layout.login), View.OnClickListener {

    private var loginViewModel: LoginViewModel? = null
    private var imei = ""

    private lateinit var btnLogin: AppCompatButton
    private lateinit var edtTxtEmpId: EditText
    private lateinit var edtTxtPassword: EditText

    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.login)

        btnLogin = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener(this)
        edtTxtEmpId = findViewById(R.id.edtTxtEmpId)
        edtTxtEmpId.setOnClickListener(this)
        edtTxtPassword = findViewById(R.id.edtTxtPassword)
        edtTxtPassword.setOnClickListener(this)

        builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        imei = this.getDeviceId().toString()

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        checkImei()

        builder.setPositiveButton("OK") { _, _ ->
            finish()
        }
    }

    private fun checkImei() {
        loginViewModel!!.checkImei(imei).observe(
            this
        ) { t ->
            if (t != null) {
                if (t.isSuccess) {
                    edtTxtEmpId.isEnabled = true
                    edtTxtPassword.isEnabled = true
                } else {
                    builder.setTitle(t.message + " (" + imei + ")")
                    builder.show()
                }
            }
        }
    }

    private fun loginCheck(empId: String, password: String) {
        loginViewModel!!.login(imei, empId, password).observe(
            this
        ) { t ->
            if (t != null) {
                if (t.isSuccess) {
                    val intent = Intent(this, Home::class.java)
                    intent.putExtra("MPNO", edtTxtEmpId.text.trim().toString())
                    intent.putExtra("NAME", t.empName)
                    intent.putExtra("TOKEN", t.token)
                    startActivity(intent)
                    finish()
                } else {
                    this.toast(t.message)
                }
            }
            hideProgressBar()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnLogin -> {
                if (edtTxtEmpId.text.trim().length in 9..10) {
                    if (edtTxtPassword.text.trim().length > 3) {
                        this.showProgressBar()
                        loginCheck(
                            edtTxtEmpId.text.trim().toString(),
                            edtTxtPassword.text.trim().toString()
                        )
                    } else
                        this.toast("Enter a valid Password")
                } else
                    this.toast("Enter a valid Emp.id")
            }
        }
    }

}