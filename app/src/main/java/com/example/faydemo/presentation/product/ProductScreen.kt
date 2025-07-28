package com.example.faydemo.presentation.product

import android.app.Notification
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.faydemo.R
import com.example.faydemo.data.models.ProductModel
import com.example.faydemo.ui.components.ActionText
import com.example.faydemo.ui.components.FayIconButton
import com.example.faydemo.ui.components.FayOutlinedTextField
import com.example.faydemo.ui.components.FayRoundedCorner
import com.example.faydemo.ui.components.ScreenPadding
import com.example.faydemo.ui.theme.EcoGradeA
import com.example.faydemo.ui.theme.EcoGradeB
import com.example.faydemo.ui.theme.EcoGradeC
import com.example.faydemo.ui.theme.EcoGradeD
import com.example.faydemo.ui.theme.EcoGradeE
import com.example.faydemo.ui.theme.EcoGradeF
import com.example.faydemo.ui.theme.Outline
import java.nio.file.WatchEvent
import java.util.Locale
import java.util.Locale.getDefault

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

    ScreenPadding(innerPadding) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            BarcodeEntry(
                barcode = state.queryBarcode,
                getProduct = viewModel::tryGetProduct,
                setBarcode = viewModel::setBarcode
            )

            AnimatedVisibility(visible = state.product != null) {
                state.product?.let {
                    ProductSheet(it, {
                        viewModel.clearProduct()
                    })
                }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductSheet(
    product: ProductModel,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        modifier = Modifier
            .padding(top = 0.dp),
        sheetState = sheetState,
        dragHandle = {},
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Product Information", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.weight(1f))
                    ActionText("Done", style = MaterialTheme.typography.titleMedium) { onDismiss() }
                }

                AsyncImage(
                    model =
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(product.image)
                            //.placeholder(R.drawable.generic_icon)
                            .build(),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .height(200.dp)
                        .clip(FayRoundedCorner)
                        .border(
                            1.dp,
                            color = Outline,
                            shape = FayRoundedCorner
                        ),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = product.name, style = MaterialTheme.typography.titleLarge)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_barcode_24),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("(${product.barcode})")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        EcoGrade(product.ecoGrade)
                    }
                }
            }
        }
    }
}

@Composable
fun EcoGrade(ecoGrade: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .border(
                    width = 2.dp,
                    shape = FayRoundedCorner,
                    color = Outline
                ),
            contentAlignment = Alignment.Center
        ) {
            val grade = ecoGrade.uppercase(getDefault())
            Text(
                modifier = Modifier,
                text = grade,
                fontWeight = FontWeight.Bold,
                color = ecoGradeToColor(grade.first()),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 35.sp)
            )
        }
        Text(text = stringResource(R.string.environment_grade))
    }
}

fun ecoGradeToColor(grade: Char): Color {
    return when (grade.uppercaseChar()) {
        'A' -> EcoGradeA
        'B' -> EcoGradeB
        'C' -> EcoGradeC
        'D' -> EcoGradeD
        'E' -> EcoGradeE
        'F' -> EcoGradeF
        else -> Color.Gray // fallback for unknown grades
    }
}

@Composable
fun BarcodeEntry(
    barcode: String,
    getProduct: () -> Unit,
    setBarcode: (barcode: String) -> Unit,
) {
    Column {
        Text("Barcode")
        FayOutlinedTextField(
            placeholder = { Text(stringResource(R.string.enter_barcode)) },
            trailingIcon = {
                Row {

                    FayIconButton(
                        modifier = Modifier.size(35.dp),
                        internalPadding = 0.dp,
                        onClick = getProduct
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = stringResource(R.string.get_product)
                        )
                    }
                }

            },
            value = barcode,
            onValueChange = setBarcode
        )
    }
}


