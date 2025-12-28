🔹 Job-01 Project Overview

Project Name: Koraish_20_REST01
Goal:

REST API থেকে product data আনা

RecyclerView দিয়ে list দেখানো

Image load করা

Internet না থাকলে Retry দেখানো

API Used:

https://api.escuelajs.co/api/v1/products

🔹 1️⃣ Project Structure (Final)

===== Structure ======
com.example.koraish_20_rest01
│
├── api
│   ├── ApiClient.kt
│   └── ApiService.kt
│
├── model
│   └── Product.kt
│
├── adapter
│   └── ProductAdapter.kt
│
├── viewmodel
│   └── ProductViewModel.kt
│
├── ui
│   └── ProductActivity.kt
│
├── utils
│   └── NetworkUtils.kt
│
└── res
    ├── layout
    │   ├── activity_product.xml
    │   └── row_product.xml
    └── drawable

🔹 2️⃣ MVVM Architecture কীভাবে কাজ করছে
🔸 MVVM মানে কী?
Layer	কাজ
Model	Data structure (Product)
View	XML + Activity
ViewModel	Business logic + API call
🔸 MVVM Flow (এক লাইনে)
API → Model → ViewModel → LiveData → Activity → RecyclerView → UI

🔹 3️⃣ Data কোথা থেকে কোথায় যাচ্ছে (Full Flow)
Step-by-step Flow 👇
✅ Step 1: API কল

ApiService.kt → getProducts()

API থেকে JSON response আসে

✅ Step 2: JSON → Kotlin Object

Gson JSON parse করে

Product.kt model এ data convert হয়

✅ Step 3: ViewModel Data ধরে

ProductViewModel API call করে

LiveData<List<Product>> এ data রাখে

✅ Step 4: Activity observe করে

ProductActivity LiveData observe করে

Data পেলে RecyclerView adapter set করে

✅ Step 5: Adapter UI বানায়

ProductAdapter প্রতিটা product row বানায়

Glide দিয়ে image load হয়

🔹 4️⃣ প্রতিটা File Explanation (সবচেয়ে গুরুত্বপূর্ণ অংশ)
🟦 Product.kt (Model)
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val images: List<String>
)

🔍 কাজ:

API JSON structure অনুযায়ী data রাখে

Gson এই class দেখে JSON parse করে

👉 Model layer

🟦 ApiService.kt (API Contract)
@GET("products")
suspend fun getProducts(): Response<List<Product>>

🔍 কাজ:

কোন endpoint call হবে সেটার rule

Retrofit এখান থেকে API call করে

👉 Network rule define করে

🟦 ApiClient.kt (Retrofit Setup)
object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.escuelajs.co/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService =
        retrofit.create(ApiService::class.java)
}

🔍 কাজ:

Retrofit initialize করে

API service object তৈরি করে

👉 API call করার engine

🟦 ProductViewModel.kt (Brain of App)
class ProductViewModel : ViewModel() {

    val products = MutableLiveData<List<Product>>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun fetchProducts() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = ApiClient.apiService.getProducts()
                if (response.isSuccessful) {
                    products.value = response.body()
                } else {
                    error.value = "Error loading data"
                }
            } catch (e: Exception) {
                error.value = "No internet connection"
            }
            isLoading.value = false
        }
    }
}

🔍 কাজ:

API call করা

Data process করা

UI কে LiveData দিয়ে notify করা

👉 MVVM এর core

🟦 ProductActivity.kt (View Layer)
viewModel.products.observe(this) {
    adapter.submitList(it)
}

🔍 কাজ:

XML UI show করা

ViewModel observe করা

RecyclerView setup করা

Retry button handle করা

👉 User interaction handle করে

🟦 ProductAdapter.kt (RecyclerView Adapter)
Glide.with(holder.itemView.context)
    .load(product.images.firstOrNull())
    .into(holder.binding.productImage)

🔍 কাজ:

Data → UI bind করা

Row layout fill করা

Image load করা

👉 List UI builder

🟦 activity_product.xml
Contains:

RecyclerView

ProgressBar

Retry Button

👉 Main screen UI

🟦 row_product.xml
Contains:

ImageView

Product name

Price

Description

👉 Single product design

🟦 NetworkUtils.kt
fun isConnected(context: Context): Boolean

🔍 কাজ:

Internet আছে কিনা check করা

No internet হলে Retry দেখানো

👉 Connectivity handling

🔹 5️⃣ Examiner / Viva-তে বলার মতো Short Explanation

👉 “এই app-এ আমি MVVM architecture use করেছি।
API call ViewModel থেকে হচ্ছে, JSON data Model এ parse হচ্ছে,
LiveData দিয়ে Activity observe করছে,
RecyclerView Adapter UI render করছে।
Image loading এর জন্য Glide ব্যবহার করেছি
এবং internet না থাকলে retry feature implement করেছি।”
