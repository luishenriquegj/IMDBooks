package com.example.imdbooks.sharedPreferencesUtils

import android.content.Context
import android.content.SharedPreferences
import com.example.imdbooks.Product
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

        fun saveProductsMutableList(context: Context, products: MutableList<Product>) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("productsPref", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(products)
            editor.putString("products", json)
            editor.apply()
        }

        fun getProducts(context: Context): MutableList<Product> {
            val sharedPreferences = context.getSharedPreferences("productsPref", Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("products", null)
            val type = object : TypeToken<MutableList<Product>>() {}.type
            return gson.fromJson(json, type) ?: mutableListOf()
        }

        fun deleteProductById(context: Context, productId: Number): Boolean {
            val sharedPreferences = context.getSharedPreferences("productsPref", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = sharedPreferences.getString("products", null)
            val productType = object : TypeToken<MutableList<Product>>() {}.type
            val currentProducts: MutableList<Product> = gson.fromJson(json, productType)

            val iterator = currentProducts.iterator()
            while (iterator.hasNext()) {
                val product = iterator.next()
                if (product.id.toInt() == productId) {
                    iterator.remove()
                    val jsonProducts = gson.toJson(currentProducts)
                    editor.putString("products", jsonProducts)
                    editor.apply()
                    return true
                }
            }
            return false
        }


    }
}

