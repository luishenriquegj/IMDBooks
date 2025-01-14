package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdbooks.databinding.ActivityListBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils

class ListActivity : AppCompatActivity(){
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentProducts: MutableList<Product> = Utils.getProducts(this)

        val recyclerView = binding.bookRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProductAdapter(currentProducts) { product ->
            val intent = Intent(this, BookDetailActivity::class.java)
            intent.putExtra("bookName", product.name)
            intent.putExtra("publisher", product.publisher)
            intent.putExtra("publishDate", product.publishedDate)
            intent.putExtra("bookCoverUrl", product.bookCover)
            intent.putExtra("bookDescription", product.description) // Passando a descrição
            startActivity(intent)
        }

        val returnBtn = binding.button
        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // Atualiza a lista de produtos se necessário
        val currentProducts: MutableList<Product> = Utils.getProducts(this)
        val recyclerView = binding.bookRecyclerView
        val adapter = recyclerView.adapter as ProductAdapter
        adapter.updateProducts(currentProducts)  // Você pode criar um método para atualizar a lista
    }
}