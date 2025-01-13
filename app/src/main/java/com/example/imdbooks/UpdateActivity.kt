package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityUpdateBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils

class UpdateActivity : AppCompatActivity(){
    private lateinit var binding: ActivityUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookName = binding.bookNameInput
        val bookCode = binding.bookCodeInput
        val bookStock = binding.stockInput
        val bookDescription = binding.bookDescInput

        val saveBtn = binding.saveButton
        val clearBtn = binding.clearButton
        val returnBtn = binding.returnBtn


        saveBtn.setOnClickListener {
            if(bookCode.text.isEmpty()){
                Toast.makeText(this, "Id is required to update a book", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if( bookName.text.isEmpty() && bookStock.text.isEmpty() && bookDescription.text.isEmpty()){
                Toast.makeText(this, "Fill at least one of the fields to updated a book", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val currentProducts = Utils.getProducts(this)

            val existingProduct = currentProducts.find { it.id.toInt() == bookCode.text.toString().toInt() }

            if (!currentProducts.any { it.id.toInt() == bookCode.text.toString().toInt() }) {
                Toast.makeText(this, "A book with this ID  doesn't exist. Please use a different ID.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (existingProduct == null) {
                return@setOnClickListener
            }

            bookStock.text.toString().toIntOrNull()?.let {
                existingProduct.stock = it
            }
            if (bookName.text.isNotEmpty()) {
                existingProduct.name = bookName.text.toString()
            }
            if (bookDescription.text.isNotEmpty()) {
                existingProduct.description = bookDescription.text.toString()
            }

            Utils.saveProductsMutableList(this, currentProducts)

            Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        clearBtn.setOnClickListener {
            bookName.text.clear()
            bookStock.text.clear()
            bookDescription.text.clear()
            bookCode.text.clear()
        }

        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

    }
}