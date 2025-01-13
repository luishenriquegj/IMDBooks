package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityRegisterBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils
import com.example.imdbooks.sharedPreferencesUtils.Utils.Companion.escapeJsonString

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val saveBtn = binding.saveButton
        val clearBtn = binding.clearButton
        val returnBtn = binding.returnBtn

        val bookNameInput = binding.bookNameInput
        val bookCodeInput = binding.bookCodeInput
        val bookStockInput = binding.stockInput
        val bookDescriptionInput = binding.bookDescInput

        saveBtn.setOnClickListener {
            val bookName = binding.bookNameInput.text.toString().trim()
            val bookCode = binding.bookCodeInput.text.toString().toIntOrNull()
            val bookStock = binding.stockInput.text.toString().toIntOrNull()
            val bookDescription = escapeJsonString(binding.bookDescInput.text.toString().trim())
            val currentProducts = Utils.getProducts(this)

            if (bookCode !=null  && bookCode > 0 ) {
                when {
                    currentProducts.any { it.id.toInt() == bookCode.toInt() } ->{
                        Toast.makeText(this, "A book with this ID already exists. Please use a different ID.", Toast.LENGTH_LONG).show()

                    }

                    bookName.isEmpty() -> {
                        Toast.makeText(this, "Please add a valid book name", Toast.LENGTH_LONG).show()
                    }

                    bookStock == null || bookStock <= 0 -> {
                        Toast.makeText(this, "Please add a valid stock quantity (greater than 0)", Toast.LENGTH_LONG).show()
                    }

                    bookDescription.isEmpty() -> {
                        Toast.makeText(this, "Please add a book description", Toast.LENGTH_LONG).show()
                    }

                    else -> {

                        val book = Product(
                            bookName,
                            bookCode,
                            bookDescription,
                            bookStock
                        )
                        currentProducts.add(book)

                        Utils.saveProductsMutableList(this, currentProducts)

                        Toast.makeText(this, "Product saved successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MenuActivity::class.java))
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Please add a valid book code (a positive number)", Toast.LENGTH_LONG).show()
            }
        }


        clearBtn.setOnClickListener {
            bookNameInput.text.clear()
            bookStockInput.text.clear()
            bookDescriptionInput.text.clear()
            bookCodeInput.text.clear()
            Toast.makeText(this, "All field values were cleared", Toast.LENGTH_SHORT).show()
        }

        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}
