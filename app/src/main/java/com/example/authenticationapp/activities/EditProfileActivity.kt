package com.example.authenticationapp.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.authenticationapp.Database
import com.example.authenticationapp.R
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val sharedPrefs = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val email = sharedPrefs.getString("email", null)
        emailAddressTextView.text = "$email"
        val db = this.openOrCreateDatabase("users", MODE_PRIVATE, null)
        val database = Database(db)
        newFirstName.setText(database.getFirstName(email))
        newLastName.setText(database.getLastName(email))


        editProfilePicture.setImageURI(Uri.parse(database.getProfilePicture(email)))

        var selectedImage : Uri? = null

        val imageLoader = registerForActivityResult(ActivityResultContracts.GetContent()) {
            selectedImage = it
            editProfilePicture.setImageURI(selectedImage)
        }

        editProfilePicture.setOnClickListener {
                imageLoader.launch("image/*")
        }

        confirmEditProfileButton.setOnClickListener {
            val gd = GradientDrawable()
            val gd2 = GradientDrawable()
            newFirstName.background = gd
            newLastName.background = gd2

            val newFirstNameCorrect : Boolean
            val newLastNameCorrect : Boolean

            if(newFirstName.text.isEmpty()){
                newFirstName.error = "You must fill this block"
                gd.setStroke(5, Color.RED)
                newFirstNameCorrect = false
            }else{
                gd2.setStroke(2, Color.TRANSPARENT)
                newFirstNameCorrect = true
            }
            if(newLastName.text.isEmpty()){
                newLastName.error = "You must fill this block"
                gd2.setStroke(5, Color.RED)
                newLastNameCorrect = false
            }else{
                gd2.setStroke(2, Color.TRANSPARENT)
                newLastNameCorrect = true
            }
            if(newFirstNameCorrect && newLastNameCorrect){
                database.updateName(newFirstName.text.toString(), newLastName.text.toString(), email)
                database.updateProfilePicture(selectedImage.toString(), email)
                Toast.makeText(this, "Changes Saved", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, HomeScreenActivity::class.java))
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }
}