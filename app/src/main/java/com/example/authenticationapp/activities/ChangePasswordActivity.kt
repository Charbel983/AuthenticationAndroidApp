package com.example.authenticationapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.authenticationapp.R
import com.example.authenticationapp.viewModels.ChangePasswordViewModel
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val viewModel = ViewModelProvider(this).get(ChangePasswordViewModel::class.java)

        confirmChangePasswordButton.setOnClickListener {
            val sharedPrefs = getSharedPreferences("autoLogin", MODE_PRIVATE)
            val email = sharedPrefs.getString("email", null)
            viewModel.setData(oldPasswordEditText, newPasswordEditText, confirmNewPasswordEditText)
            viewModel.updatePassword(this, email)
        }
    }
}