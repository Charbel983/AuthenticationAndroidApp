package com.example.authenticationapp.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.authenticationapp.Database
import com.example.authenticationapp.R
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        confirmChangePasswordButton.setOnClickListener {
            val gd = GradientDrawable()
            val gd2 = GradientDrawable()
            val gd3 = GradientDrawable()
            oldPasswordEditText.background = gd
            newPasswordEditText.background = gd2
            confirmNewPasswordEditText.background = gd3

            val oldPasswordCorrect : Boolean
            val newPasswordCorrect : Boolean
            val confirmNewPasswordCorrect : Boolean

            val db = this.openOrCreateDatabase("users", MODE_PRIVATE, null)
            val database = Database(db)
            val sharedPrefs = getSharedPreferences("autoLogin", MODE_PRIVATE)
            val email = sharedPrefs.getString("email", null)

            if(oldPasswordEditText.text.toString() != database.getPassword(email)){
                oldPasswordEditText.error = "Does not match old password"
                gd.setStroke(5, Color.RED)
                oldPasswordCorrect = false
            }else{
                gd.setStroke(2, Color.GRAY)
                oldPasswordCorrect = true
            }
            if(newPasswordEditText.text.toString().length < 6){
                newPasswordEditText.error = "Password must be at least 6 characters"
                gd2.setStroke(5, Color.RED)
                newPasswordCorrect = false
            }else{
                gd2.setStroke(2, Color.GRAY)
                newPasswordCorrect = true
            }
            if(confirmNewPasswordEditText.text.toString() != newPasswordEditText.text.toString()){
                confirmNewPasswordEditText.error = "Does not match previous password"
                gd3.setStroke(5, Color.RED)
                confirmNewPasswordCorrect = false
            }else{
                gd3.setStroke(2, Color.GRAY)
                confirmNewPasswordCorrect = true
            }

            if(oldPasswordCorrect && newPasswordCorrect && confirmNewPasswordCorrect){
                database.updatePassword(newPasswordEditText.text.toString(), email)
                Toast.makeText(this, "Password Changed", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, HomeScreenActivity::class.java))
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }
}