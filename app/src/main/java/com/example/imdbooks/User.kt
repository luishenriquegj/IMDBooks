package com.example.imdbooks

class User(private val username: String, private val password: String) {

    fun getPassword(): String {
        return password
    }
    fun getUsername(): String {
        return username
    }

    override fun toString(): String {
        return "User(username='$username')"
    }
}