package com.example.imdbooks

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class BookDetailActivity : AppCompatActivity() {

    private lateinit var bookCover: ImageView
    private lateinit var bookName: TextView
    private lateinit var publisher: TextView
    private lateinit var publishDate: TextView
    private lateinit var bookDescription: TextView
    private lateinit var returnBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail) // Aponte para o XML que você forneceu

        // Inicializando as Views
        bookCover = findViewById(R.id.bookCover)
        bookName = findViewById(R.id.bookName)
        publisher = findViewById(R.id.publisherValue)
        publishDate = findViewById(R.id.publishDate)
        bookDescription = findViewById(R.id.bookDescription)
        returnBtn = findViewById(R.id.buttonReturn)

        // Recuperando os dados passados através da Intent
        val bookCoverUrl = intent.getStringExtra("bookCoverUrl")
        val bookNameValue = intent.getStringExtra("bookName")
        val publisherValue = intent.getStringExtra("publisher")
        val publishDateValue = intent.getStringExtra("publishDate")
        val bookDescriptionValue = intent.getStringExtra("bookDescription")

        // Usando Glide para carregar a imagem da capa
        Glide.with(this)
            .load(bookCoverUrl) // URL da imagem a ser carregada
            .into(bookCover)    // Exibe a imagem no ImageView `bookCover`

        // Exibindo os dados na interface
        bookDescription.text = bookDescriptionValue
        bookName.text = bookNameValue
        publisher.text = publisherValue
        publishDate.text = publishDateValue

        // Configurando o botão de retorno
        returnBtn.setOnClickListener {
            finish() // Volta para a tela anterior
        }
    }
}
