package com.example.faydemo.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.faydemo.data.FayResult
import com.example.faydemo.data.ProductRepository
import com.example.faydemo.data.models.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductState(
    val queryBarcode: String = "",
    val product: ProductModel? = null,
    val error: String? = null,
    val isLoadingProduct: Boolean = false,
)

sealed class ProductNavEvent {

}

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepository: ProductRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductState())
    val uiState = _uiState.asStateFlow()


    fun init(){
        //nothing to do
    }

    fun setBarcode(newBarcode: String) {
        _uiState.update { it.copy(queryBarcode = newBarcode) }
    }

    fun startLoading() {
        _uiState.update { it.copy(isLoadingProduct = true) }
    }

    fun stopLoading() {
        _uiState.update { it.copy(isLoadingProduct = false) }
    }

    /**
     * use query barcode to get product
     * api will rate limit at 100r/m so please only call this when the user is ready
     * to search
     */
    fun tryGetProduct() {
        val barcode = _uiState.value.queryBarcode
        viewModelScope.launch {
            startLoading()
            when (val result = productRepository.getProduct(barcode)) {
                is FayResult.Error -> {
                    _uiState.update {
                        it.copy(
                            product = null,
                            error = "something went wrong",
                        )
                    }
                }

                is FayResult.Success -> {
                    _uiState.update {
                        it.copy(
                            product = result.data
                        )
                    }
                }
            }

            stopLoading()
        }
    }


}