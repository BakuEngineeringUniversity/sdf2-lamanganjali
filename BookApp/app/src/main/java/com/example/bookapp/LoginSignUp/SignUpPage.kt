package com.example.bookapp.LoginSignUp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
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
import com.google.gson.Gson


class SignUpPage : AppCompatActivity() {

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        apiService = ApiClient.getClient().create(ApiService::class.java)

        val emailEditText = findViewById<EditText>(R.id.emailInput2)
        val passwordEditText = findViewById<EditText>(R.id.passwordInput3)
        val repasswordEditText = findViewById<EditText>(R.id.passwordInput2)
        val signupButton = findViewById<Button>(R.id.signUpButton)

        signupButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val repassword = repasswordEditText.text.toString().trim()

            if (validateInputs(email, password, repassword)) {
                registerUser(email, password)
            }
        }
    }

    private fun validateInputs(email: String, password: String, repassword: String): Boolean {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        if (password != repassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun registerUser(email: String, password: String) {
        val user = User(username = email, password = password)

        apiService.register(user).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("Registration Response", "Response Code: ${response.code()}")


                if ( response.code()==200) {
                    Toast.makeText(this@SignUpPage, "Registration Successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@SignUpPage, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@SignUpPage, "Registration Failed", Toast.LENGTH_SHORT).show()
                    Log.e("Registration Error", "Response Code: ${response.code()}")

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@SignUpPage, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Registration Error", "Error: ${t.message}")
            }
        })
    }
}