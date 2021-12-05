package com.example.authenticationapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Paint
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.authenticationapp.R
import com.example.authenticationapp.viewModels.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signupButton.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        val emailText : EditText = findViewById(R.id.emailText)
        val passwordText : EditText = findViewById(R.id.passwordText)
        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        val sharedPrefs = getSharedPreferences("autoLogin", Context.MODE_PRIVATE)
        val j = sharedPrefs.getInt("key", 0)
        if (j > 0) {
            val activity = Intent(applicationContext, HomeScreenActivity::class.java)
            startActivity(activity)
        }

        loginButton.setOnClickListener{
            viewModel.setData(emailText, passwordText)

            if(viewModel.checkAuthentication(this)){
                val autoSave = 1
                val editor = sharedPrefs.edit()
                editor.putInt("key", autoSave)
                editor.putString("email", emailText.text.toString())
                editor.apply()
                val home = Intent(this, HomeScreenActivity::class.java)
                startActivity(home)
            }else{
                Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_LONG).show()
            }
        }
        signupButton.setOnClickListener {
            val signup = Intent(this, SignupActivity::class.java)
            startActivity(signup)
        }
    }
}