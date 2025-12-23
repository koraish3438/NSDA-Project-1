package com.example.koraish_20_rest01.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koraish_20_rest01.api.ApiClient
import com.example.koraish_20_rest01.model.Product
import kotlinx.coroutines.launch

class ProductViewModel  : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    init {
        refreshProducts()
    }

    fun refreshProducts() {
        viewModelScope.launch {
            fetchProducts()
        }
    }

    private suspend fun fetchProducts() {
        try {
            val response = ApiClient.apiService.getProducts()
            if (response.isSuccessful && response.body() != null) {
                _products.postValue(response.body())
            } else {
                _products.postValue(emptyList())
            }
        } catch (e: Exception) {
            Log.e("ProductViewModel", "Error fetching products", e)
            _products.postValue(emptyList())
        }
    }
}