package com.example.koraish_20_rest01

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.koraish_20_rest01.adapter.ProductAdapter
import com.example.koraish_20_rest01.databinding.ActivityProductBinding
import com.example.koraish_20_rest01.viewmodel.ProductViewModel

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        productAdapter = ProductAdapter(emptyList())
        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }

        productViewModel.products.observe(this) { products ->
            products?.let { productAdapter.updateProducts(it) }
        }

        binding.refreshBtn.setOnClickListener { productViewModel.refreshProducts() }
    }
}