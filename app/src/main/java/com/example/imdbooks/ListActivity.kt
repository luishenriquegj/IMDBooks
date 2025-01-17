package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdbooks.databinding.ActivityListBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils

// Atividade responsável por exibir uma lista de produtos (livros) em um RecyclerView.
class ListActivity : AppCompatActivity(){

    private lateinit var binding: ActivityListBinding        // Binding é usado para acessar diretamente os elementos do layout sem necessidade de `findViewById`.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)       // Infla o layout usando View Binding.
        setContentView(binding.root)
        val currentProducts: MutableList<Product> = Utils.getProducts(this)     // Recupera a lista de produtos (livros) armazenados em SharedPreferences.

        // Configura o RecyclerView para exibir os produtos.
        val recyclerView = binding.bookRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this) // Define o layout como uma lista vertical.
        recyclerView.adapter = ProductAdapter(currentProducts) { product ->

            // Quando um item da lista é clicado, cria uma Intent para a BookDetailActivity.
            val intent = Intent(this, BookDetailActivity::class.java)
            intent.putExtra("bookName", product.name) // Passa o nome do livro.
            intent.putExtra("publisher", product.publisher) // Passa o nome do publisher.
            intent.putExtra("publishDate", product.publishedDate) // Passa a data de publicação.
            intent.putExtra("bookCoverUrl", product.bookCover) // Passa a URL da capa do livro.
            intent.putExtra("bookDescription", product.description) // Passando a descrição do livro.
            startActivity(intent) // Inicia a BookDetailActivity.
        }

        // Configura o botão de retorno para voltar ao menu principal.
        val returnBtn = binding.button
        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java)) // Redireciona para o MenuActivity.
            finish() // Finaliza a atividade atual.
        }
    }

    override fun onResume() {
        super.onResume()
        // Atualiza a lista de produtos se necessário
        val currentProducts: MutableList<Product> = Utils.getProducts(this)
        val recyclerView = binding.bookRecyclerView
        // Atualiza o adaptador com os novos produtos.
        val adapter = recyclerView.adapter as ProductAdapter
        adapter.updateProducts(currentProducts) // Metodo criado no adaptador para atualizar a lista.
    }
}