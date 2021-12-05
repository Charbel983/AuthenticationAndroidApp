package com.example.authenticationapp.viewModels

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.authenticationapp.R
import com.example.authenticationapp.activities.HomeScreenActivity
import com.example.authenticationapp.database.UsersDB

class ChangePasswordViewModel : ViewModel() {

    private val oldPassword = MutableLiveData<EditText>()
    private val newPassword = MutableLiveData<EditText>()
    private val confirmNewPassword = MutableLiveData<EditText>()

    fun setData(oldPassword : EditText, newPassword : EditText, confirmNewPassword : EditText) {
        this.oldPassword.value = oldPassword
        this.newPassword.value = newPassword
        this.confirmNewPassword.value = confirmNewPassword
    }

    fun updatePassword(context: Context, email :String?) {
        val database = UsersDB.get(context).getUsersDAO()
        val gd = GradientDrawable()
        val gd2 = GradientDrawable()
        val gd3 = GradientDrawable()
        oldPassword.value!!.background = gd
        newPassword.value!!.background = gd2
        confirmNewPassword.value!!.background = gd3

        val oldPasswordCorrect : Boolean
        val newPasswordCorrect : Boolean
        val confirmNewPasswordCorrect : Boolean


        if(oldPassword.value!!.text.toString() != database.getPassword(email)){
            oldPassword.value!!.error = context.getString(R.string.doesNotMatchOldPass)
            gd.setStroke(5, Color.RED)
            oldPasswordCorrect = false
        }else{
            gd.setStroke(2, Color.GRAY)
            oldPasswordCorrect = true
        }
        when {
            newPassword.value!!.text.toString().length < 6 -> {
                newPassword.value!!.error = context.getString(R.string.passwordError)
                gd2.setStroke(5, Color.RED)
                newPasswordCorrect = false
            }
            newPassword.value!!.text.toString() == oldPassword.value!!.text.toString() -> {
                newPassword.value!!.error = context.getString(R.string.samePassword)
                gd2.setStroke(5, Color.RED)
                newPasswordCorrect = false
            }
            else -> {
                gd2.setStroke(2, Color.GRAY)
                newPasswordCorrect = true
            }
        }
        if(confirmNewPassword.value!!.text.toString() != newPassword.value!!.text.toString()){
            confirmNewPassword.value!!.error = context.getString(R.string.passwordDoesNotMatch)
            gd3.setStroke(5, Color.RED)
            confirmNewPasswordCorrect = false
        }else{
            gd3.setStroke(2, Color.GRAY)
            confirmNewPasswordCorrect = true
        }
        if(oldPasswordCorrect && newPasswordCorrect && confirmNewPasswordCorrect){
            database.updatePassword(email, newPassword.value!!.text.toString())
            Toast.makeText(context, context.getString(R.string.passwordChanged), Toast.LENGTH_LONG).show()
            context.startActivity(Intent(context, HomeScreenActivity::class.java))
        }else{
            Toast.makeText(context, context.getString(R.string.somethingWentWrong), Toast.LENGTH_LONG).show()
        }
    }
}