package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityDeleteBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils.Companion.deleteProductById

class DeleteActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val deleteBtn = binding.deleteBtn
        val cancelBtn = binding.cancelBtn

        deleteBtn.setOnClickListener {
            val bookIdInput = binding.bookIdInput.text.toString().toIntOrNull()

            if (bookIdInput == null) {
                Toast.makeText(this, "Please insert a valid book ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!deleteProductById(this,bookIdInput)){
                Toast.makeText(this, "Product with Id not found!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                Toast.makeText(this, "Product deleted with success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
                return@setOnClickListener
            }



        }

        cancelBtn.setOnClickListener {
            startActivity(Intent(this@DeleteActivity, MenuActivity::class.java))
            finish()
            return@setOnClickListener
        }
    }
}
