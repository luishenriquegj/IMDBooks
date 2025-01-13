package com.example.imdbooks

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonSyntaxException

class ProductAdapter(private var bookList: MutableList<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookName: TextView = itemView.findViewById(R.id.bookName)
        val bookCode: TextView = itemView.findViewById(R.id.bookCode)
        val bookStock: TextView = itemView.findViewById(R.id.bookStock)
        val bookDescription: TextView = itemView.findViewById(R.id.bookDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val book: Product = bookList[position]
        try {
            holder.bookName.text = book.name
            holder.bookCode.text = book.id.toString()
            holder.bookStock.text = book.stock.toString()
            holder.bookDescription.text = book.description
        } catch (e: JsonSyntaxException) {
            Log.e("ProductAdapter", "Failed to parse book JSON", e)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }



}
