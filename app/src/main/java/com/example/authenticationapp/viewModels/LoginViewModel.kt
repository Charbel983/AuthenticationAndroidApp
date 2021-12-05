package com.example.authenticationapp.viewModels

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.authenticationapp.R
import com.example.authenticationapp.database.UsersDB

class LoginViewModel : ViewModel() {

    private val email = MutableLiveData<EditText>()
    private val password = MutableLiveData<EditText>()

    fun setData(emailField: EditText, passwordField: EditText) {
        email.value = emailField
        password.value = passwordField
    }

    fun checkAuthentication(context: Context) : Boolean {
        val gd = GradientDrawable()
        val gd2 = GradientDrawable()
        email.value!!.background = gd
        password.value!!.background = gd2
        if(TextUtils.isEmpty(email.value!!.text) || !Patterns.EMAIL_ADDRESS.matcher(email.value!!.text).matches()){
            email.value!!.error = context.getString(R.string.emailFormatError)
            gd.setStroke(5, Color.RED)
        }else{
            gd.setStroke(2, Color.TRANSPARENT)
        }
        if(password.value!!.text.length < 6) {
            password.value!!.error = context.getString(R.string.passwordError)
            gd2.setStroke(5, Color.RED)
        }else{
            gd2.setStroke(2, Color.TRANSPARENT)
        }
        return UsersDB.get(context).getUsersDAO().emailAndPasswordCheck(email.value!!.text.toString(), password.value!!.text.toString())
    }
}