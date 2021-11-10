package com.example.authenticationapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.content.SharedPreferences




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signupButton.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        val database = this.openOrCreateDatabase("users", MODE_PRIVATE, null)
        fun emailAndPasswordCheck(emailCheck : String, passwordCheck : String) : Boolean {
            try{
                val cursor = database.rawQuery("SELECT email FROM user WHERE email = '$emailCheck' AND password = '$passwordCheck'", null)
                if(cursor.moveToFirst()){
                    database.close()
                    return true
                }
            }catch(errorException : Exception){
                database.close()
            }
            return false
        }

        val sharedPrefs = getSharedPreferences("autoLogin", Context.MODE_PRIVATE)
        val j = sharedPrefs.getInt("key", 0)
        if (j > 0) {
            val activity = Intent(applicationContext, HomeScreenActivity::class.java)
            startActivity(activity)
        }

        loginButton.setOnClickListener{
            val gd = GradientDrawable()
            val gd2 = GradientDrawable()
            emailText.background = gd
            passwordText.background = gd2
            if(!TextUtils.isEmpty(emailText.text) && Patterns.EMAIL_ADDRESS.matcher(emailText.text).matches()){
                gd.setStroke(2,Color.TRANSPARENT)
            }else{
                emailText.error = "Not a valid email format"
                gd.setStroke(5,Color.RED)
            }
            if(passwordText.text.length < 6){
                passwordText.error = "Password must be at least 6 characters"
                gd2.setStroke(5, Color.RED)
            }else{
                gd2.setStroke(2, Color.TRANSPARENT)
            }
            if(emailAndPasswordCheck(emailText.text.toString(), passwordText.text.toString())){
                val autoSave = 1
                val editor = sharedPrefs.edit()
                editor.putInt("key", autoSave)
                editor.apply()
                val home = Intent(this, HomeScreenActivity::class.java)
                home.putExtra("Email", emailText.text.toString())
                startActivity(home)
            }else{
                Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_LONG)
            }
        }
        signupButton.setOnClickListener {
            val signup = Intent(this, SignupActivity::class.java)
            startActivity(signup)
        }
    }
}