package com.example.authenticationapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.authenticationapp.R
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        aboutUsWebView.settings.javaScriptEnabled = true
        aboutUsWebView.loadUrl(getString(R.string.webViewURL))

    }
}