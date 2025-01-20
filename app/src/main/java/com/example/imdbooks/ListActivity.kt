package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdbooks.databinding.ActivityListBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils

// Atividade responsável por exibir uma lista de produtos (livros) em um RecyclerView.
class ListActivity : AppCompatActivity(){

    private lateinit var binding: ActivityListBinding        // Binding é usado para acessar diretamente os elementos do layout sem necessidade de `findViewById`.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentProducts: MutableList<Product> = Utils.getProducts(this)

        val recyclerView = binding.bookRecyclerView
        val emptyMessage = binding.emptyMessage

        if (currentProducts.isEmpty()) {
            // Exibe a mensagem e oculta o RecyclerView
            emptyMessage.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            // Oculta a mensagem e exibe o RecyclerView
            emptyMessage.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = ProductAdapter(currentProducts) { product ->
                val intent = Intent(this, BookDetailActivity::class.java)
                intent.putExtra("bookName", product.name)
                intent.putExtra("publisher", product.publisher)
                intent.putExtra("publishDate", product.publishedDate)
                intent.putExtra("bookCoverUrl", product.bookCover)
                intent.putExtra("bookDescription", product.description)
                startActivity(intent)
            }
        }

        val returnBtn = binding.button
        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val currentProducts: MutableList<Product> = Utils.getProducts(this)
        val recyclerView = binding.bookRecyclerView
        val emptyMessage = binding.emptyMessage

        if (currentProducts.isEmpty()) {
            emptyMessage.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyMessage.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            val adapter = recyclerView.adapter as? ProductAdapter
            adapter?.updateProducts(currentProducts) ?: run {
                recyclerView.adapter = ProductAdapter(currentProducts) { product ->
                    val intent = Intent(this, BookDetailActivity::class.java)
                    intent.putExtra("bookName", product.name)
                    intent.putExtra("publisher", product.publisher)
                    intent.putExtra("publishDate", product.publishedDate)
                    intent.putExtra("bookCoverUrl", product.bookCover)
                    intent.putExtra("bookDescription", product.description)
                    startActivity(intent)
                }
            }
        }
    }
}
