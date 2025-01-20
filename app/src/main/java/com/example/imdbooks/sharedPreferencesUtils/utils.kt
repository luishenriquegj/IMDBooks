package com.example.imdbooks.sharedPreferencesUtils

import android.content.Context
import android.content.SharedPreferences
import com.example.imdbooks.Product
import com.example.imdbooks.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Utils {

    companion object {
        fun escapeJsonString(input: String): String {
            val stringBuilder = StringBuilder()
            for (char in input) {
                when (char) {
                    '\\' -> stringBuilder.append("\\\\")
                    '"' -> stringBuilder.append("\\\"")
                    '\b' -> stringBuilder.append("\\b")
                    '\n' -> stringBuilder.append("\\n")
                    '\r' -> stringBuilder.append("\\r")
                    '\t' -> stringBuilder.append("\\t")
                    else -> stringBuilder.append(char)
                }
            }
            return stringBuilder.toString()
        }

        fun saveProductsMutableList(context: Context, books: MutableList<Product>) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("booksPref", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(books)
            editor.putString("books", json)
            editor.apply()
        }

        fun saveUsersMutableList(context: Context, users: MutableList<User>) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(users)
            editor.putString("users", json)
            editor.apply()
        }

        fun getProducts(context: Context): MutableList<Product> {
            val sharedPreferences = context.getSharedPreferences("booksPref", Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("books", null)
            val type = object : TypeToken<MutableList<Product>>() {}.type
            return gson.fromJson(json, type) ?: mutableListOf()
        }

        fun deleteProductById(context: Context, bookId: Number): Boolean {
            val sharedPreferences = context.getSharedPreferences("booksPref", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = sharedPreferences.getString("books", null)
            val bookType = object : TypeToken<MutableList<Product>>() {}.type

            // Garante que a lista não seja nula
            val currentProducts: MutableList<Product> = gson.fromJson(json, bookType) ?: mutableListOf()

            val iterator = currentProducts.iterator()
        while (iterator.hasNext()) {
            val book = iterator.next()
            if (book.id.toInt() == bookId) {
                iterator.remove()
                val jsonProducts = gson.toJson(currentProducts)
                editor.putString("books", jsonProducts)
                editor.apply()
                return true
        }
    }
    return false
}

    }
}

