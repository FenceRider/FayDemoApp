import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class ProductResponse(
    val code: String,
    val product: Product? = null, // Product object can be null if not found
    val status: Int? = null,
    val status_verbose: String? = null
)

@Serializable
data class Product(
    val product_name: String,
    val image_url: String,
    val ecoscore_score: Int? = null,
    val ecoscore_grade: String? = null
)