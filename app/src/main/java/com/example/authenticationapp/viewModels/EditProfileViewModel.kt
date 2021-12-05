package com.example.authenticationapp.viewModels

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.authenticationapp.R
import com.example.authenticationapp.database.UsersDB

class EditProfileViewModel : ViewModel() {

    private val editProfilePicture = MutableLiveData<Uri>()
    val profilePicture : LiveData<Uri>
    get() = editProfilePicture
    private val newFirstName = MutableLiveData<String>()
    val firstName : LiveData<String>
    get() = newFirstName
    private val newLastName = MutableLiveData<String>()
    val lastName : LiveData<String>
    get() = newLastName
    private val emailAddress = MutableLiveData<String>()
    val emailAdr : LiveData<String>
    get() = emailAddress

    fun setData(email : String?, context: Context){
        val database = UsersDB.get(context).getUsersDAO()
        newFirstName.value = database.getFirstName(email)
        newLastName.value = database.getLastName(email)
        editProfilePicture.value = Uri.parse(database.getProfilePicture(email))
        emailAddress.value = email
    }

    fun checkFields(newFirstName : EditText, newLastName : EditText, context: Context) : Boolean {
        val gd = GradientDrawable()
        val gd2 = GradientDrawable()
        newFirstName.background = gd
        newLastName.background = gd2

        val newFirstNameCorrect : Boolean
        val newLastNameCorrect : Boolean

        if(newFirstName.text.isEmpty()){
            newFirstName.error = context.getString(R.string.blockEmpty)
            gd.setStroke(5, Color.RED)
            newFirstNameCorrect = false
        }else{
            gd2.setStroke(2, Color.TRANSPARENT)
            newFirstNameCorrect = true
        }
        if(newLastName.text.isEmpty()){
            newLastName.error = context.getString(R.string.blockEmpty)
            gd2.setStroke(5, Color.RED)
            newLastNameCorrect = false
        }else{
            gd2.setStroke(2, Color.TRANSPARENT)
            newLastNameCorrect = true
        }
        return newFirstNameCorrect && newLastNameCorrect
    }
}