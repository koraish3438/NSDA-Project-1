package com.example.koraish_20_rest01.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koraish_20_rest01.databinding.ProductItemBinding
import com.example.koraish_20_rest01.model.Product

class ProductAdapter(var products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = products[position]
        holder.binding.productName.text = currentItem.title
        holder.binding.productPrice.text = "$${currentItem.price}"
        holder.binding.productDescription.text = currentItem.description
        Glide.with(holder.binding.root.context)
            .load(currentItem.images.firstOrNull())
            .into(holder.binding.productImage)
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}