package com.example.imdbooks

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonSyntaxException

// Adapter para o RecyclerView, usado para exibir uma lista de livros (Products).
// Recebe uma lista inicial de livros (bookList) e uma função lambda (onItemClicked) para manipular cliques nos itens.
class ProductAdapter(
    private var bookList: MutableList<Product>, // Lista de produtos (livros) que serão exibidos
    private val onItemClicked: (Product) -> Unit // Callback para manipulação de cliques em itens
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // Metodo para atualizar a lista de produtos com novos dados
    fun updateProducts(newProducts: MutableList<Product>?) {
        if (newProducts != null) { // Garante que a nova lista não seja nula
            bookList = newProducts // Substitui a lista atual pela nova lista
        }
    }

    // Classe interna que representa o ViewHolder, responsável por armazenar referências às views de cada item
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookName: TextView = itemView.findViewById(R.id.bookName) // TextView para o nome do livro
        val publisherText: TextView = itemView.findViewById(R.id.publisherValue) // TextView para o nome do editor
        val publishedDate: TextView = itemView.findViewById(R.id.publishDate) // TextView para a data de publicação
        val bookCover :ImageView =itemView.findViewById(R.id.bookCover) // ImageView para a capa do livro
    }

    // Criação do ViewHolder, inflando o layout XML de um item da lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view) // Retorna um novo ViewHolder com o layout inflado
    }

    // Vincula os dados do livro à view correspondente no ViewHolder
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val book: Product = bookList[position] // Obtém o livro na posição atual da lista

        try {
            // Define os valores das views com os dados do livro
            holder.bookName.text = book.name
            holder.publisherText.text = book.publisher
            holder.publishedDate.text = book.publishedDate
            // Usa o Glide para carregar a imagem da capa do livro
            Glide.with(holder.itemView.context)
                .load(book.bookCover) // URL da capa do livro
                .into(holder.bookCover) // Define a imagem carregada na ImageView

            // Define um listener para cliques no item
            holder.itemView.setOnClickListener {
                // Cria uma Intent para abrir a tela de detalhes do livro
                val intent = Intent(holder.itemView.context, BookDetailActivity::class.java)
                intent.putExtra("bookName", book.name) // Passa o nome do livro como extra
                intent.putExtra("publisher", book.publisher) // Passa o nome do editor como extra
                intent.putExtra("publishDate", book.publishedDate) // Passa a data de publicação como extra
                intent.putExtra("bookCoverUrl", book.bookCover) // Passa a URL da capa como extra
                intent.putExtra("bookDescription", book.description) // Passa a descrição do livro
                holder.itemView.context.startActivity(intent) // Inicia a atividade
            }
        } catch (e: JsonSyntaxException) { // Captura exceções relacionadas a JSON malformado
            Log.e("ProductAdapter", "Failed to parse book JSON", e) // Loga o erro
        }
    }

    // Retorna o tamanho da lista de livros
    override fun getItemCount(): Int {
        return bookList.size
    }
}
