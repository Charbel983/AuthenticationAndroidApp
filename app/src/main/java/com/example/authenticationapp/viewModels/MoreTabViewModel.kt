package com.example.authenticationapp.viewModels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.authenticationapp.database.UsersDB

class MoreTabViewModel : ViewModel() {

    private val profilePicture = MutableLiveData<Uri>()
    val profilePic : LiveData<Uri> get() = profilePicture
    private val emailAddress = MutableLiveData<String>()
    val emailAdr : LiveData<String> get() = emailAddress
    private val fullName = MutableLiveData<String>()
    val name : LiveData<String> get() = fullName

    fun setData(email : String?, context: Context) {
        val database = UsersDB.get(context).getUsersDAO()
        profilePicture.value = Uri.parse(database.getProfilePicture(email))
        emailAddress.value = email
        fullName.value = database.getFirstName(email) + " " +  database.getLastName(email)
    }
}