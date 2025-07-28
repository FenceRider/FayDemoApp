package com.example.faydemo.data.models

data class ProductModel(
    val barcode: String,
    val name: String,
    val image: String,
    val ecoScore: Int?,
    val ecoGrade: String?

)