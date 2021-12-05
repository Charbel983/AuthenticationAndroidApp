package com.example.authenticationapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.authenticationapp.R
import com.example.authenticationapp.database.UsersDB
import com.example.authenticationapp.viewModels.EditProfileViewModel
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    lateinit var viewModel : EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val sharedPrefs = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val email = sharedPrefs.getString("email", null)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        val database = UsersDB.get(this.application).getUsersDAO()

        viewModel.setData(email, this.application)

        var selectedImage : Uri? = null
        viewModel.profilePicture.observe(this, { selected -> editProfilePicture.setImageURI(selected) })
        viewModel.emailAdr.observe(this, {selected -> emailAddressTextView.text = selected })
        viewModel.firstName.observe(this, {selected -> newFirstName.setText(selected)})
        viewModel.lastName.observe(this, {selected -> newLastName.setText(selected)})


        val imageLoader = registerForActivityResult(ActivityResultContracts.GetContent()) {
            selectedImage = it
            editProfilePicture.setImageURI(selectedImage)
        }

        editProfilePicture.setOnClickListener {
                imageLoader.launch("image/*")
        }

        confirmEditProfileButton.setOnClickListener {
            if(viewModel.checkFields(newFirstName, newLastName, this)){
                database.updateName(email, newFirstName.text.toString(), newLastName.text.toString())
                database.updateProfilePicture(email, selectedImage.toString())
                Toast.makeText(this, getString(R.string.changesSaved), Toast.LENGTH_LONG).show()
                startActivity(Intent(this, HomeScreenActivity::class.java))
            }else{
                Toast.makeText(this, getString(R.string.somethingWentWrong), Toast.LENGTH_LONG).show()
            }
        }
    }
}