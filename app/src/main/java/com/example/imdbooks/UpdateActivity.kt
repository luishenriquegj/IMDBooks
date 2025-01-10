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

        val productName = binding.productNameInput
        val productCode = binding.productCodeInput
        val productStock = binding.stockInput
        val productDescription = binding.productDescInput

        val saveBtn = binding.saveButton
        val clearBtn = binding.clearButton
        val returnBtn = binding.returnBtn


        saveBtn.setOnClickListener {
            if(productCode.text.isEmpty()){
                Toast.makeText(this, "Id is required to update a product", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if( productName.text.isEmpty() && productStock.text.isEmpty() && productDescription.text.isEmpty()){
                Toast.makeText(this, "Fill at least one of the fields to updated a product", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val currentProducts = Utils.getProducts(this)

            val existingProduct = currentProducts.find { it.id.toInt() == productCode.text.toString().toInt() }

            if (!currentProducts.any { it.id.toInt() == productCode.text.toString().toInt() }) {
                Toast.makeText(this, "A product with this ID  doesn't exist. Please use a different ID.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (existingProduct == null) {
                return@setOnClickListener
            }

            productStock.text.toString().toIntOrNull()?.let {
                existingProduct.stock = it
            }
            if (productName.text.isNotEmpty()) {
                existingProduct.name = productName.text.toString()
            }
            if (productDescription.text.isNotEmpty()) {
                existingProduct.description = productDescription.text.toString()
            }

            Utils.saveProductsMutableList(this, currentProducts)

            Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        clearBtn.setOnClickListener {
            productName.text.clear()
            productStock.text.clear()
            productDescription.text.clear()
            productCode.text.clear()
        }

        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

    }
}