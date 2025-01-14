package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityMenuBinding


class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addProdBtn = binding.addProductBtn
        val listBtn = binding.listProductBtn
        val removeProductBtn = binding.removeProductBtn
        val updateProductBtn = binding.updateProductBtn

        addProdBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        listBtn.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
            finish()
        }

        removeProductBtn.setOnClickListener {
            startActivity(Intent(this, DeleteActivity::class.java))
            finish()
        }

        updateProductBtn.setOnClickListener {
            startActivity(Intent(this, UpdateActivity::class.java))
            finish()
        }
    }
}