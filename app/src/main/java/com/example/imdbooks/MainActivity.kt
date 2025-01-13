package com.example.imdbooks

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityMainBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var userPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPrefs = getSharedPreferences("userCredentials", MODE_PRIVATE)


        val allUsers = Utils.getAllUsers(this)
        val loginBtn = binding.loginBtn
        val passwordEdt = binding.loginPassword
        val emailEdt = binding.loginEmail
        val createNewUseBtn = binding.registerBtn

        loginBtn.setOnClickListener {
            val inputEmail = emailEdt.text.toString()
            val inputPassword = passwordEdt.text.toString()


            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please enter a username and a matching password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when {
                allUsers.any {
                    it.getUsername() == inputEmail && it.getPassword() == inputPassword
                } -> {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity,MenuActivity::class.java))
                }
                else -> {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
        createNewUseBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity,CreateNewUserActivity::class.java))
        }
    }


}
