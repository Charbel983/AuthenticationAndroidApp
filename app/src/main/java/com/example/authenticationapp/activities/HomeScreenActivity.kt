package com.example.authenticationapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.authenticationapp.Database
import com.example.authenticationapp.R
import com.example.authenticationapp.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_home_screen.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val database = this.openOrCreateDatabase("users", MODE_PRIVATE, null)
        val sharedPrefs = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val emailAuto = sharedPrefs.getString("email", null)
        val obj = Database(database)
        val firstName = obj.getFirstName(emailAuto)
        val lastName = obj.getLastName(emailAuto)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        welcomeText.append("Welcome $firstName $lastName")

        val adapter = ViewPagerAdapter(this)
        homeViewPager.adapter = adapter



        TabLayoutMediator(tabSelector, homeViewPager, fun(tab: TabLayout.Tab, position){
            if(position == 1){
                tab.text = "More"
            }else{
                tab.text = "News"
            }
        }).attach()


        signoutButton.setOnClickListener {
            val editor = sharedPrefs.edit()
            editor.putInt("key", 0)
            editor.apply()

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}