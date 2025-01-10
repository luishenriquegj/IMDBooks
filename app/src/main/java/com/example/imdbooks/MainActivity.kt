package com.example.imdbooks

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
        const val FIRST_START_KEY = "first_start"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var userPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPrefs = getSharedPreferences("userCredentials", MODE_PRIVATE)

        if (userPrefs.getBoolean(FIRST_START_KEY, true)) {
            initializeDefaultUser()
        }

        val loginBtn = binding.loginBtn
        val passwordEdt = binding.loginPassword
        val emailEdt = binding.loginEmail

        loginBtn.setOnClickListener {
            val inputEmail = emailEdt.text.toString()
            val inputPassword = passwordEdt.text.toString()

            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val storedEmail = userPrefs.getString(EMAIL_KEY, null)
            val storedPassword = userPrefs.getString(PASSWORD_KEY, null)

            if (inputEmail == storedEmail && inputPassword == storedPassword) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MainActivity,MenuActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeDefaultUser() {
        val editor = userPrefs.edit()
        editor.putString(EMAIL_KEY, "admin")
        editor.putString(PASSWORD_KEY, "admin")
        editor.putBoolean(FIRST_START_KEY, false)
        editor.apply()
        Toast.makeText(this, "Default user created: admin/admin", Toast.LENGTH_SHORT).show()
    }
}
