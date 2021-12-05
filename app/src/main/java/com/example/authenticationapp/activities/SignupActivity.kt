package com.example.authenticationapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.authenticationapp.R
import com.example.authenticationapp.viewModels.SignupViewModel
import kotlinx.android.synthetic.main.activity_signup.*

open class SignupActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)


        createAccountButton.setOnClickListener{
            viewModel.setData(firstNameText, lastNameText, signupEmailText, signupPasswordText, confirmPasswordText)
            viewModel.checkAuthentication(this)
        }
    }
}