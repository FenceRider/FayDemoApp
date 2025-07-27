package com.example.faydemo.data

import com.example.faydemo.data.models.ProductModel
import com.example.faydemo.network.ProductRequest
import com.example.faydemo.network.doRequest
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val productRequest: ProductRequest
) {

    suspend fun getProduct(barcode: String): FayResult<ProductModel, Nothing> {
        return when (val result = consume(productRequest.getProduct(barcode))) {
            is FayResult.Error -> result
            is FayResult.Success -> {
                result.data?.let {
                    if (it.product == null) {
                        null
                    } else {
                        FayResult.Success(
                            ProductModel(
                                barcode = it.code,
                                name = it.product.product_name,
                                image = it.product.image_url,
                                ecoScore = it.product.ecoscore_score,
                                ecoGrade = it.product.ecoscore_grade
                            )
                        )
                    }
                } ?: FayResult.Error(FayError.GenericError, null)
            }
        }
    }
}