package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdbooks.ProductAdapter
import com.example.imdbooks.databinding.ActivityListBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils

class ListActivity : AppCompatActivity(){
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currentProducts: MutableList<Product> = Utils.getProducts(this)

        val recyclerView = binding.productRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProductAdapter(currentProducts)
    }

    override fun onResume() {
        super.onResume()
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currentProducts: MutableList<Product> = Utils.getProducts(this)
        val returnBtn = binding.button
        val recyclerView = binding.productRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProductAdapter(currentProducts)


        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}