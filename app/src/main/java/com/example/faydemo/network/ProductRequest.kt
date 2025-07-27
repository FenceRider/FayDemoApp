package com.example.faydemo.network


import com.example.faydemo.di.OpenNetwork
import com.example.faydemo.network.models.ProductResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class ProductRequest @Inject constructor(
    @OpenNetwork
    private val client: HttpClient
) {
    suspend fun getProduct(
        barcode: String
    ): FayHTTPResponse<ProductResponse> =
        doRequest {
            client.get(
                "https://world.openfoodfacts.net/api/v2/product/$barcode?product_type=all&fields=knowledge_panels"
            )
        }

}