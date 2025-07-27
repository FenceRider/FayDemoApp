package com.example.faydemo.presentation.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.faydemo.R
import com.example.faydemo.data.models.ProductModel
import com.example.faydemo.presentation.login.LoginViewModel
import com.example.faydemo.ui.components.FayOutlinedTextField

@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(), innerPadding: PaddingValues, onBack: () -> Unit
) {


    val state by viewModel.uiState.collectAsState()

    var initialized by rememberSaveable { mutableStateOf(false) }

    if (!initialized) {
        LaunchedEffect(Unit) {
            viewModel.init()
            initialized = true
        }
    }


    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        Text("Barcode")
        FayOutlinedTextField(
            placeholder = { Text(stringResource(R.string.enter_barcode)) },
            value = state.queryBarcode,
            onValueChange = viewModel::setBarcode
        )
        Button(onClick = viewModel::tryGetProduct) { Text("Get Product") }
        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            state.product?.let { product ->
                Column {
                    Text("productName ${product.name}")
                    Text("barcode ${product.barcode}")
                    Text("ecoGrade ${product.ecoGrade}")
                    Text("ecoScore ${product.ecoScore}")
                    Text("image url ${product.image}")
                }
            }

        }
    }


}