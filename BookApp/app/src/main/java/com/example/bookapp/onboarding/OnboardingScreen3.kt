package com.example.bookapp.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookapp.LoginSignUp.LoginPage
import com.example.bookapp.R

class OnboardingScreen3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding_screen3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nextButton = findViewById<Button>(R.id.button)
        val backButton = findViewById<Button>(R.id.button2)
        nextButton.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }
        backButton.setOnClickListener {
            val intent = Intent(this, OnboardingScreen2::class.java)
            startActivity(intent)
            finish()
        }
    }
}