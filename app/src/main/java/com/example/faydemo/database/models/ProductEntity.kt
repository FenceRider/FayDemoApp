package com.example.faydemo.database.models

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert


@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    val barcode: String,
    val name: String,
    val image: String,
    val ecoScore: Int?,
    val ecoGrade: String?
)

@Dao
interface ProductDao {
    @Query(
        """
            SELECT * FROM product
        """
    )
    fun getProducts(): List<ProductEntity>


    @Query(
        """
            SELECT * FROM product WHERE barcode = :barcode LIMIT 1
        """
    )
    fun getProduct(barcode: String): ProductEntity?

    @Upsert
    fun upsertProduct(product: ProductEntity)
}