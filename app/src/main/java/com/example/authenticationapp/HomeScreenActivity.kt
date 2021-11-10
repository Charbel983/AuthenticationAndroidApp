package com.example.authenticationapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home_screen.*
import android.content.SharedPreferences




class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val database = this.openOrCreateDatabase("users", MODE_PRIVATE, null)
        val emailRef = intent.getStringExtra("Email")
        val cursor = database.rawQuery("SELECT firstName, lastName FROM user WHERE email = '$emailRef'", null)
        var firstName = ""
        var lastName = ""
        if(cursor.count > 0){
            cursor.moveToFirst()
            firstName = cursor.getString(0)
            lastName = cursor.getString(1)
        }
        cursor.close()
        database.close()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val sharedPrefs = getSharedPreferences("autoLogin", Context.MODE_PRIVATE)
        val save = getSharedPreferences("data", Context.MODE_PRIVATE)
        save.edit().putString("firstName", firstName).apply()
        save.edit().putString("lastName", lastName).apply()
        val fs = save.getString("firstName", null)
        val ls = save.getString("lastName", null)
        welcomeText.append("Welcome $fs $ls")

        signoutButton.setOnClickListener {
            val editor = sharedPrefs.edit()
            editor.putInt("key", 0)
            editor.apply()

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}