package com.example.authenticationapp.viewModels

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.authenticationapp.R
import com.example.authenticationapp.activities.MainActivity
import com.example.authenticationapp.database.User
import com.example.authenticationapp.database.UsersDB

class SignupViewModel : ViewModel() {
    private val firstName = MutableLiveData<EditText>()
    private val lastName = MutableLiveData<EditText>()
    private val email = MutableLiveData<EditText>()
    private val password = MutableLiveData<EditText>()
    private val confirmPassword = MutableLiveData<EditText>()

    fun setData(firstNameField : EditText, lastNameField : EditText, emailField : EditText, passwordField : EditText, confirmPasswordField : EditText) {
        firstName.value = firstNameField
        lastName.value = lastNameField
        email.value = emailField
        password.value = passwordField
        confirmPassword.value = confirmPasswordField
    }

    fun checkAuthentication(context: Context) {
        val gd = GradientDrawable()
        val gd2 = GradientDrawable()
        val gd3 = GradientDrawable()
        val gd4 = GradientDrawable()
        val gd5 = GradientDrawable()
        email.value!!.background = gd
        password.value!!.background = gd2
        confirmPassword.value!!.background = gd3
        firstName.value!!.background = gd4
        lastName.value!!.background = gd5
        val firstNameCorrect : Boolean
        val lastNameCorrect : Boolean
        val emailCorrect : Boolean
        val passwordCorrect : Boolean
        val confirmPasswordCorrect : Boolean
        var emailExists = true

        if(firstName.value!!.text.isEmpty()){
            firstName.value!!.error = context.getString(R.string.blockEmpty)
            gd4.setStroke(5, Color.RED)
            firstNameCorrect = false
        }else{
            gd4.setStroke(2, Color.TRANSPARENT)
            firstNameCorrect = true
        }
        if(lastName.value!!.text.isEmpty()){
            lastName.value!!.error = context.getString(R.string.blockEmpty)
            gd5.setStroke(5, Color.RED)
            lastNameCorrect = false
        }else{
            gd5.setStroke(2, Color.TRANSPARENT)
            lastNameCorrect = true
        }
        if(TextUtils.isEmpty(email.value!!.text) || !Patterns.EMAIL_ADDRESS.matcher(email.value!!.text).matches()){
            gd.setStroke(5, Color.RED)
            email.value!!.error = context.getString(R.string.emailFormatError)
            emailCorrect = false
        }
        else{
            gd.setStroke(2, Color.TRANSPARENT)
            emailCorrect = true
        }
        if(UsersDB.get(context).getUsersDAO().emailAlreadyExists(email.value!!.text.toString())){
            gd.setStroke(5, Color.RED)
            email.value!!.error = context.getString(R.string.emailExists)
        }else{
            gd.setStroke(2, Color.TRANSPARENT)
            emailExists = false
        }
        if(password.value!!.text.length < 6){
            password.value!!.error = context.getString(R.string.passwordError)
            gd2.setStroke(5, Color.RED)
            passwordCorrect = false
        }else{
            gd2.setStroke(2, Color.TRANSPARENT)
            passwordCorrect = true
        }
        if(confirmPassword.value!!.text.toString() != password.value!!.text.toString()){
            confirmPassword.value!!.error = context.getString(R.string.passwordDoesNotMatch)
            gd3.setStroke(5, Color.RED)
            confirmPasswordCorrect = false
        }else{
            gd3.setStroke(2, Color.TRANSPARENT)
            confirmPasswordCorrect = true
        }
        if(firstNameCorrect && lastNameCorrect && emailCorrect && passwordCorrect && confirmPasswordCorrect && !emailExists){
            val userFirstName = firstName.value!!.text.toString()
            val userLastName = lastName.value!!.text.toString()
            val userEmail = email.value!!.text.toString()
            val userPassword = password.value!!.text.toString()
            val defaultImage = Uri.parse(context.getString(R.string.defaultProfile)).toString()
            val user = User(userFirstName, userLastName, userEmail, userPassword, defaultImage)
            UsersDB.get(context).getUsersDAO().addUser(user)
            val login = Intent(context, MainActivity::class.java)
            context.startActivity(login)
        }else{
            Toast.makeText(context,context.getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT).show()
        }
    }
}