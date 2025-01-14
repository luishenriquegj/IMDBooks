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

class ProductAdapter(private var bookList: MutableList<Product>, private val onItemClicked: (Product) -> Unit)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun updateProducts(newProducts: MutableList<Product>?) {
        if (newProducts != null) {
            bookList = newProducts
        }
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookName: TextView = itemView.findViewById(R.id.bookName)
        val publisherText: TextView = itemView.findViewById(R.id.publisherValue)
        val publishedDate: TextView = itemView.findViewById(R.id.publishDate)
        val bookCover :ImageView =itemView.findViewById(R.id.bookCover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val book: Product = bookList[position]

        try {
            holder.bookName.text = book.name
            holder.publisherText.text = book.publisher
            holder.publishedDate.text = book.publishedDate
            Glide.with(holder.itemView.context)
                .load(book.bookCover)
                .into(holder.bookCover)

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, BookDetailActivity::class.java)
                intent.putExtra("bookName", book.name)
                intent.putExtra("publisher", book.publisher)
                intent.putExtra("publishDate", book.publishedDate)
                intent.putExtra("bookCoverUrl", book.bookCover)
                intent.putExtra("bookDescription", book.description) // Adicionando a descrição do livro
                holder.itemView.context.startActivity(intent)
            }

        } catch (e: JsonSyntaxException) {
            Log.e("ProductAdapter", "Failed to parse book JSON", e)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}
