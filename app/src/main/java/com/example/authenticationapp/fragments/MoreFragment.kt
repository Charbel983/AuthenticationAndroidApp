package com.example.authenticationapp.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.database.Cursor
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.authenticationapp.Database
import com.example.authenticationapp.R
import com.example.authenticationapp.activities.AboutUsActivity
import com.example.authenticationapp.activities.ChangePasswordActivity
import com.example.authenticationapp.activities.EditProfileActivity
import kotlinx.android.synthetic.main.fragment_more.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val parentHolder = inflater.inflate(R.layout.fragment_more, container, false)
        val aboutUsButton = parentHolder.findViewById<TextView>(R.id.aboutUsButton)
        val fullNameText = parentHolder.findViewById<TextView>(R.id.fullNameTextView)
        val emailText = parentHolder.findViewById<TextView>(R.id.emailTextView)
        val editProfileButton = parentHolder.findViewById<Button>(R.id.editProfileButton)
        val changePasswordButton = parentHolder.findViewById<Button>(R.id.changePasswordButton)
        val sharedPrefs = this.activity?.getSharedPreferences("autoLogin", MODE_PRIVATE)
        val email = sharedPrefs?.getString("email", null)
        val database = this.activity?.openOrCreateDatabase("users", MODE_PRIVATE, null)
        val obj = Database(database)
        val firstName = obj.getFirstName(email)
        val lastName = obj.getLastName(email)
        val profilePicture = parentHolder.findViewById<ImageView>(R.id.profilePicture)


        val image = obj.getProfilePicture(email)
        profilePicture.setImageURI(Uri.parse(image))
        emailText.text = "$email"
        fullNameText.text = "$firstName $lastName"
        aboutUsButton.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        editProfileButton.setOnClickListener{
            startActivity(Intent(this.context, EditProfileActivity::class.java))
        }
        changePasswordButton.setOnClickListener{
            startActivity(Intent(this.context, ChangePasswordActivity::class.java))
        }
        aboutUsButton.setOnClickListener {
            startActivity(Intent(this.context, AboutUsActivity::class.java))
        }


        return parentHolder
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}