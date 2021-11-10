package com.example.authenticationapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*

open class SignupActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val database = baseContext.openOrCreateDatabase("users", MODE_PRIVATE, null)
        database.execSQL("CREATE TABLE IF NOT EXISTS user(firstName TEXT, lastName TEXT, email TEXT, password TEXT)")

        fun emailAlreadyExists(emailCheck : String) : Boolean {
            try{
                val cursor = database.rawQuery("SELECT email FROM user WHERE email = '$emailCheck'", null)
                if(cursor.moveToFirst()){
                    database.close()
                    return true
                }
            }catch(errorException : Exception){
                database.close()
            }
            return false
        }

        createAccountButton.setOnClickListener{
            val gd = GradientDrawable()
            val gd2 = GradientDrawable()
            val gd3 = GradientDrawable()
            val gd4 = GradientDrawable()
            val gd5 = GradientDrawable()
            signupEmailText.background = gd
            signupPasswordText.background = gd2
            confirmPasswordText.background = gd3
            firstNameText.background = gd4
            lastNameText.background = gd5
            val firstNameCorrect : Boolean
            val lastNameCorrect : Boolean
            val emailCorrect : Boolean
            val passwordCorrect : Boolean
            val confirmPasswordCorrect : Boolean
            var emailExists : Boolean = true
            if(firstNameText.text.isEmpty()){
                firstNameText.error = "You must fill this block"
                gd4.setStroke(5, Color.RED)
                firstNameCorrect = false
            }else{
                gd4.setStroke(2, Color.TRANSPARENT)
                firstNameCorrect = true
            }
            if(lastNameText.text.isEmpty()){
                lastNameText.error = "You must fill this block"
                gd5.setStroke(5, Color.RED)
                lastNameCorrect = false
            }else{
                gd5.setStroke(2, Color.TRANSPARENT)
                lastNameCorrect = true
            }
            if(!TextUtils.isEmpty(signupEmailText.text) && Patterns.EMAIL_ADDRESS.matcher(signupEmailText.text).matches()){
                gd.setStroke(2, Color.TRANSPARENT)
                emailCorrect = true
            }
            else{
                signupEmailText.error = "Not a valid email format"
                gd.setStroke(5, Color.RED)
                emailCorrect = false
            }
            if(emailAlreadyExists(signupEmailText.text.toString())){
                gd.setStroke(5, Color.RED)
                signupEmailText.error = "Email already exists"
            }else{
                gd.setStroke(2, Color.TRANSPARENT)
                emailExists = false
            }
            if(signupPasswordText.text.length < 6){
                signupPasswordText.error = "Password must be at least 6 characters"
                gd2.setStroke(5, Color.RED)
                passwordCorrect = false
            }else{
                gd2.setStroke(2, Color.TRANSPARENT)
                passwordCorrect = true
            }
            if(confirmPasswordText.text.toString() != signupPasswordText.text.toString()){
                confirmPasswordText.error = "Does not match previous password"
                gd3.setStroke(5, Color.RED)
                confirmPasswordCorrect = false
            }else{
                gd3.setStroke(2, Color.TRANSPARENT)
                confirmPasswordCorrect = true
            }
            if(firstNameCorrect && lastNameCorrect && emailCorrect && passwordCorrect && confirmPasswordCorrect && !emailExists){
                val userFirstName = firstNameText.text.toString()
                val userLastName = lastNameText.text.toString()
                val userEmail = signupEmailText.text.toString()
                val userPassword = signupPasswordText.text.toString()
                database.execSQL("INSERT INTO user(firstName, lastName, email, password) VALUES('$userFirstName', '$userLastName', '$userEmail', '$userPassword')")
                val login = Intent(this, MainActivity::class.java)
                startActivity(login)
            }else{
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
        }
    }
}