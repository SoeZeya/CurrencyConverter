package com.soezeya.currencyconverter


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Immediately redirect to MainActivity
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Prevent going back to splash
    }
}