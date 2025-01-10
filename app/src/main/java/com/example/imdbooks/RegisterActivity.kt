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

        val productNameInput = binding.productNameInput
        val productCodeInput = binding.productCodeInput
        val productStockInput = binding.stockInput
        val productDescriptionInput = binding.productDescInput

        saveBtn.setOnClickListener {
            val productName = binding.productNameInput.text.toString().trim()
            val productCode = binding.productCodeInput.text.toString().toIntOrNull()
            val productStock = binding.stockInput.text.toString().toIntOrNull()
            val productDescription = escapeJsonString(binding.productDescInput.text.toString().trim())
            val currentProducts = Utils.getProducts(this)

            if (productCode !=null  && productCode > 0 ) {
                when {
                    currentProducts.any { it.id.toInt() == productCode.toInt() } ->{
                        Toast.makeText(this, "A product with this ID already exists. Please use a different ID.", Toast.LENGTH_LONG).show()

                    }

                    productName.isEmpty() -> {
                        Toast.makeText(this, "Please add a valid product name", Toast.LENGTH_LONG).show()
                    }

                    productStock == null || productStock <= 0 -> {
                        Toast.makeText(this, "Please add a valid stock quantity (greater than 0)", Toast.LENGTH_LONG).show()
                    }

                    productDescription.isEmpty() -> {
                        Toast.makeText(this, "Please add a product description", Toast.LENGTH_LONG).show()
                    }

                    else -> {

                        val product = Product(
                            productName,
                            productCode,
                            productDescription,
                            productStock
                        )
                        currentProducts.add(product)

                        Utils.saveProductsMutableList(this, currentProducts)

                        Toast.makeText(this, "Product saved successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MenuActivity::class.java))
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Please add a valid product code (a positive number)", Toast.LENGTH_LONG).show()
            }
        }


        clearBtn.setOnClickListener {
            productNameInput.text.clear()
            productStockInput.text.clear()
            productDescriptionInput.text.clear()
            productCodeInput.text.clear()
            Toast.makeText(this, "All field values were cleared", Toast.LENGTH_SHORT).show()
        }

        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}
