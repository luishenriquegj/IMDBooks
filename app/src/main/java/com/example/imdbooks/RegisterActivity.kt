package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityRegisterBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils
import com.example.imdbooks.sharedPreferencesUtils.Utils.Companion.escapeJsonString

// Classe da atividade de registro de livros, derivada de AppCompatActivity
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding // Instância de binding para acessar as views

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)   // Inicializa o View Binding
        setContentView(binding.root)                                // Define a visualização raiz do layout

        // Referências para os botões e campos de entrada do layout
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

        // Configura o listener para o botão "Salvar"
        saveBtn.setOnClickListener {
            // Recupera os valores dos campos, realizando trim para remover espaços desnecessários
            val bookName = binding.bookNameInput.text.toString().trim()
            val bookAuthor = binding.bookAuthor.text.toString().trim()
            val bookCode = binding.bookCodeInput.text.toString().toIntOrNull() // Tenta converter o código para Int
            val bookCover = binding.bookCoverInput.text.toString().trim()
            val bookPublisher = binding.bookPublisherInput.text.toString().trim()
            val bookDescription = escapeJsonString(binding.bookDescription.text.toString().trim()) // Escapa caracteres de JSON
            val bookPublishDate = binding.bookPublishDate.text.toString().trim()
            val currentProducts = Utils.getProducts(this) // Recupera a lista atual de produtos

            // Valida o código do livro
            if (bookCode !=null  && bookCode > 0 ) {
                when {
                    // Verifica se o ID já existe na lista
                    currentProducts.any { it.id.toInt() == bookCode.toInt() } ->{
                        Toast.makeText(this, "A book with this ID already exists. Please use a different ID.", Toast.LENGTH_LONG).show()

                    }

                    // Verifica se o nome do livro foi preenchido
                    bookName.isEmpty() -> {
                        Toast.makeText(this, "Please add a valid book name", Toast.LENGTH_LONG).show()
                    }

                    // Verifica se a descrição foi preenchida
                    bookDescription.isEmpty() -> {
                        Toast.makeText(this, "Please add the book description", Toast.LENGTH_LONG).show()
                    }

                    // Verifica se o autor foi preenchido
                    bookAuthor.isEmpty() -> {
                        Toast.makeText(this, "Please add the book Author name", Toast.LENGTH_LONG).show()
                    }

                    // Verifica se a URL da capa foi preenchida
                    bookCover.isEmpty() ->{
                        Toast.makeText(this, "Please add the book cover url", Toast.LENGTH_LONG).show()
                    }

                    // Verifica se o editor foi preenchido
                    bookPublisher.isEmpty() ->{
                        Toast.makeText(this, "Please add a Publisher for the book", Toast.LENGTH_LONG).show()
                    }

                    // Verifica se a data de publicação foi preenchida
                    bookPublishDate.isEmpty() ->{
                        Toast.makeText(this, "Please add a Published date for the book", Toast.LENGTH_LONG).show()
                    }

                    else -> {
                        // Cria um objeto do tipo Product com os dados fornecidos
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

                        // Adiciona o novo livro à lista
                        currentProducts.add(book)

                        // Salva a lista atualizada no SharedPreferences
                        Utils.saveProductsMutableList(this, currentProducts)

                        // Notifica o sucesso e redireciona para o menu principal
                        Toast.makeText(this, "Product saved successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MenuActivity::class.java))
                        finish() // Finaliza a atividade atual
                    }
                }
            } else {
                // Exibe mensagem caso o código não seja válido
                Toast.makeText(this, "Please add a valid book code (a positive number)", Toast.LENGTH_LONG).show()
            }
        }

        // Configura o listener para o botão "Limpar"
        clearBtn.setOnClickListener {
            // Limpa os campos de entrada
            bookNameInput.text.clear()
            bookCoverInput.text.clear()
            bookAuthorInput.text.clear()
            bookDescriptionInput.text.clear()
            bookCodeInput.text.clear()
            bookPublisherInput.text.clear()
            bookPublishedDate.text.clear()
            // Exibe mensagem de confirmação
            Toast.makeText(this, "All field values were cleared", Toast.LENGTH_SHORT).show()
        }

        // Configura o listener para o botão "Retornar"
        returnBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java)) // Redireciona para o MenuActivity
            finish() // Finaliza a atividade atual
        }
    }
}
