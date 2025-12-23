package com.example.koraish_20_rest01.model

data class Product(
    val images: List<String>,
    val title: String,
    val price: Double,
    val description: String
)