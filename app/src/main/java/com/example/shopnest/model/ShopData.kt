package com.example.shopnest.model

data class ShopData(
    val id: Int? ,
    val title: String?,
    val price: Double? ,
    val description: String? ,
    val category: String? ,
    val image: String? = null,
    val rating: Rating? = Rating()
)

data class Rating(
    val rate: Double? = null,
    val count: Int? = null
)

