package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityUpdateBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils

// Classe que representa a Activity de atualização de informações de um livro
class UpdateActivity : AppCompatActivity(){
    private lateinit var binding: ActivityUpdateBinding // Inicializa o View Binding para acessar as views
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater) // Inflando o layout da Activity
        setContentView(binding.root) // Definindo a view raiz do layout

        // Referências para os campos de entrada
        val bookName = binding.bookNameInput // Campo de entrada para o nome do livro
        val bookCode = binding.bookCodeInput // Campo de entrada para o código do livro (ISBN)
        val bookDescription = binding.bookDescInput // Campo de entrada para a descrição do livro

        // Referências para os botões
        val saveBtn = binding.saveButton // Botão para salvar as alterações
        val clearBtn = binding.clearButton // Botão para limpar os campos
        val returnBtn = binding.returnBtn // Botão para retornar ao menu

        // Configura o listener do botão "Salvar"
        saveBtn.setOnClickListener {
            // Verifica se o código do livro foi fornecido
            if(bookCode.text.isEmpty()){
                Toast.makeText(this, "ISBN is required to update a book", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se pelo menos um campo foi preenchido
            if( bookName.text.isEmpty()  && bookDescription.text.isEmpty()){
                Toast.makeText(this, "Fill at least one of the fields to updated a book", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Recupera a lista atual de produtos armazenados
            val currentProducts = Utils.getProducts(this)

            // Procura um livro com o código fornecido
            val existingProduct = currentProducts.find { it.id.toInt() == bookCode.text.toString().toInt() }

            // Verifica se o livro existe na lista
            if (!currentProducts.any { it.id.toInt() == bookCode.text.toString().toInt() }) {
                Toast.makeText(this, "A book with this ISBN  doesn't exist. Please use a different ISBN.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Verifica novamente se o `existingProduct` é nulo.
            if (existingProduct == null) {
                return@setOnClickListener
            }

            // Atualiza o nome do livro, se fornecido
            if (bookName.text.isNotEmpty()) {
                existingProduct.name = bookName.text.toString()
            }

            // Atualiza a descrição do livro, se fornecida
            if (bookDescription.text.isNotEmpty()) {
                existingProduct.description = bookDescription.text.toString()
            }

            // Salva a lista de produtos atualizada no SharedPreferences
            Utils.saveProductsMutableList(this, currentProducts)

            // Exibe uma mensagem de sucesso e retorna ao menu
            Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MenuActivity::class.java))
            finish() // Finaliza a Atividade atual
        }

        // Configura o listener do botão "Limpar"
        clearBtn.setOnClickListener {
            bookName.text.clear() // Limpa o campo do nome
            bookDescription.text.clear() // Limpa o campo da descrição
            bookCode.text.clear() // Limpa o campo do código
        }

        // Configura o listener do botão "Retornar"
        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java)) // Navega de volta para o menu principal
            finish() // Finaliza a Atividade atual
        }

    }
}