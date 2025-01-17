package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityMenuBinding

// Atividade do Menu principal que fornece opções para gerenciar produtos (livros).
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding   // Binding é usado para acessar diretamente os elementos do layout.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)       // Infla o layout da atividade usando View Binding.
        setContentView(binding.root)

        // Referências aos botões no layout.
        val addProdBtn = binding.addProductBtn      // Botão para adicionar um novo produto.
        val listBtn = binding.listProductBtn        // Botão para listar os produtos.
        val removeProductBtn = binding.removeProductBtn // Botão para remover um produto.
        val updateProductBtn = binding.updateProductBtn // Botão para atualizar um produto.

        // Configura o comportamento do botão "Adicionar Produto".
        addProdBtn.setOnClickListener {
            // Redireciona o usuário para a atividade de cadastro de produtos.
            startActivity(Intent(this, RegisterActivity::class.java))
            finish() // Finaliza a atividade atual para evitar que ela permaneça na pilha.
        }

        // Configura o comportamento do botão "Listar Produtos".
        listBtn.setOnClickListener {
            // Redireciona o usuário para a atividade que lista os produtos.
            startActivity(Intent(this, ListActivity::class.java))
            finish() // Finaliza a atividade atual.
        }

        // Configura o comportamento do botão "Remover Produto".
        removeProductBtn.setOnClickListener {
            // Redireciona o usuário para a atividade de remoção de produtos.
            startActivity(Intent(this, DeleteActivity::class.java))
            finish() // Finaliza a atividade atual.
        }

        // Configura o comportamento do botão "Atualizar Produto".
        updateProductBtn.setOnClickListener {
            // Redireciona o usuário para a atividade de atualização de produtos.
            startActivity(Intent(this, UpdateActivity::class.java))
            finish() // Finaliza a atividade atual.
        }
    }
}