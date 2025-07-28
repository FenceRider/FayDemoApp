package com.example.faydemo.data

import com.example.faydemo.data.models.ProductModel
import com.example.faydemo.database.models.ProductDao
import com.example.faydemo.database.models.ProductEntity
import com.example.faydemo.network.ProductRequest
import com.example.faydemo.network.doRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productRequest: ProductRequest,
    private val productDao: ProductDao
) {

    suspend fun getProduct(barcode: String): FayResult<ProductModel, Nothing> = withContext(
        Dispatchers.IO
    ) {

        productDao.getProduct(barcode)?.let {
            return@withContext FayResult.Success(
                ProductModel(
                    barcode = it.barcode,
                    name = it.name,
                    image = it.image,
                    ecoScore = it.ecoScore,
                    ecoGrade = it.ecoGrade
                )
            )
        }

        return@withContext when (val result = consume(productRequest.getProduct(barcode))) {
            is FayResult.Error -> result
            is FayResult.Success -> {
                result.data?.let {
                    if (it.product == null) {
                        null
                    } else {
                        productDao.upsertProduct(
                            ProductEntity(
                                barcode = it.code,
                                name = it.product.product_name,
                                image = it.product.image_url,
                                ecoScore = it.product.ecoscore_score,
                                ecoGrade = it.product.ecoscore_grade
                            )
                        )

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

    suspend fun readProducts(): FayResult<List<ProductModel>, Nothing> {
        return withContext(Dispatchers.IO) {
            val products = productDao.getProducts()
            return@withContext FayResult.Success(products.map {
                ProductModel(
                    barcode = it.barcode,
                    name = it.name,
                    image = it.image,
                    ecoScore = it.ecoScore,
                    ecoGrade = it.ecoGrade,
                )
            })
        }
    }

}