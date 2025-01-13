package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityCreateNewUserBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils
import com.example.imdbooks.sharedPreferencesUtils.Utils.Companion.getAllUsers

class CreateNewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNewUserBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewUserBinding.inflate(layoutInflater)
        val createUserButton = binding.createUserBtn
        val returnToLoginButton = binding.returnToLoginBtn
        val usernameInput = binding.newUsernameInput
        val userPassword = binding.newPasswordInput
        setContentView(binding.root)
        val currentUsers = getAllUsers(this)

        createUserButton.setOnClickListener{
            when {
                currentUsers.any {
                    it.getPassword() == usernameInput.text.toString()
                } -> {
                    Toast.makeText(
                        this,
                        "A user with this username already exists. Please use a different username.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {

                    val user = User(
                        usernameInput.text.toString(),
                        userPassword.text.toString(),
                    )
                    currentUsers.add(user)
                    Utils.saveUsersMutableList(this, currentUsers)

                    Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))

                    finish()
                }
            }

        }

        returnToLoginButton.setOnClickListener {
            startActivity(Intent(this@CreateNewUserActivity,MainActivity::class.java))
        }

    }


}