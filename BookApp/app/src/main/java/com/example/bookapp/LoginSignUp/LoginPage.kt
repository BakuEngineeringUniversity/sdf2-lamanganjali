package com.example.bookapp.LoginSignUp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookapp.MainActivity
import com.example.bookapp.R
import com.example.bookapp.network.ApiClient
import com.example.bookapp.network.ApiService
import com.example.bookapp.network.User

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity() {

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val usernameEditText = findViewById<EditText>(R.id.emailInput)
        val passwordEditText = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val haveAccText = findViewById<TextView>(R.id.signUpText)
        haveAccText.setOnClickListener {
            val intent = Intent(this, SignUpPage::class.java)
            startActivity(intent)
        }

        apiService = ApiClient.getClient().create(ApiService::class.java)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val user = User(username = username, password = password)
                apiService.login(user).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.code()==200) {
                            Toast.makeText(this@LoginPage, "Login Successful", Toast.LENGTH_SHORT).show()
                            val Intent = Intent(this@LoginPage, MainActivity::class.java)
                            startActivity(Intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginPage, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(this@LoginPage, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        Log.e("Login Error", "Error: ${t.message}")
                    }
                })
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}