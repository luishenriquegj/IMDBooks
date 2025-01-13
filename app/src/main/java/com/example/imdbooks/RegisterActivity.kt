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
        val bookCoverInput = binding.bookCoverInput
        val bookDescriptionInput = binding.bookDescription
        val bookAuthorInput = binding.bookAuthor
        val bookPublisherInput = binding.bookPublisherInput
        val bookPublishedDate= binding.bookPublishDate

        saveBtn.setOnClickListener {
            val bookName = binding.bookNameInput.text.toString().trim()
            val bookAuthor = binding.bookAuthor.text.toString().trim()
            val bookCode = binding.bookCodeInput.text.toString().toIntOrNull()
            val bookCover = binding.bookCoverInput.text.toString().trim()
            val bookPublisher = binding.bookPublisherInput.text.toString().trim()
            val bookDescription = escapeJsonString(binding.bookDescription.text.toString().trim())
            val bookPublishDate = binding.bookPublishDate.text.toString().trim()
            val currentProducts = Utils.getProducts(this)

            if (bookCode !=null  && bookCode > 0 ) {
                when {
                    currentProducts.any { it.id.toInt() == bookCode.toInt() } ->{
                        Toast.makeText(this, "A book with this ID already exists. Please use a different ID.", Toast.LENGTH_LONG).show()

                    }

                    bookName.isEmpty() -> {
                        Toast.makeText(this, "Please add a valid book name", Toast.LENGTH_LONG).show()
                    }

                    bookDescription.isEmpty() -> {
                        Toast.makeText(this, "Please add the book description", Toast.LENGTH_LONG).show()
                    }
                    bookAuthor.isEmpty() -> {
                        Toast.makeText(this, "Please add the book Author name", Toast.LENGTH_LONG).show()
                    }
                    bookCover.isEmpty() ->{
                        Toast.makeText(this, "Please add the book cover url", Toast.LENGTH_LONG).show()
                    }
                    bookPublisher.isEmpty() ->{
                        Toast.makeText(this, "Please add a Publisher for the book", Toast.LENGTH_LONG).show()
                    }
                    bookPublishDate.isEmpty() ->{
                        Toast.makeText(this, "Please add a Published date for the book", Toast.LENGTH_LONG).show()
                    }

                    else -> {
                        println(bookCover)
                        val book = Product(
                            bookName,
                            bookCode,
                            bookDescription,
                            bookCover,
                            bookPublisher,
                            bookAuthor,
                            bookPublishDate,
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
            bookCoverInput.text.clear()
            bookAuthorInput.text.clear()
            bookDescriptionInput.text.clear()
            bookCodeInput.text.clear()
            bookPublisherInput.text.clear()
            bookPublishedDate.text.clear()
            Toast.makeText(this, "All field values were cleared", Toast.LENGTH_SHORT).show()
        }

        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}
