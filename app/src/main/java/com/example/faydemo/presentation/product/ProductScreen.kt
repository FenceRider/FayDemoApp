package com.example.faydemo.presentation.product

import android.app.Notification
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.faydemo.R
import com.example.faydemo.data.models.ProductModel
import com.example.faydemo.presentation.login.ActionSection
import com.example.faydemo.presentation.login.LoginSection
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
        val configuration = LocalConfiguration.current
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(.5f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        SelectionPane(
                            queryBarcode = state.queryBarcode,
                            tryGetProduct = viewModel::tryGetProduct,
                            setBarcode = viewModel::setBarcode,
                            seenProducts = state.seenProducts,
                            selectedBarcode = state.product?.barcode
                        )

                    }

                    Box(
                        modifier = Modifier
                            .weight(.5f)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.animation.AnimatedVisibility(
                            state.product != null,
                        ) {
                            state.product?.let { product ->

                                ProductPane(
                                    onDismiss = viewModel::clearProduct,
                                    product = product
                                )

                            }
                        }
                        androidx.compose.animation.AnimatedVisibility(
                            state.product == null,
                        ) {
                            ProductPanePlaceholder(isLoading = false)
                        }
                    }
                }

            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    SelectionPane(
                        queryBarcode = state.queryBarcode,
                        tryGetProduct = viewModel::tryGetProduct,
                        setBarcode = viewModel::setBarcode,
                        seenProducts = state.seenProducts,
                        selectedBarcode = state.product?.barcode
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


    }
}


@Composable
fun SelectionPane(
    queryBarcode: String,
    selectedBarcode: String?,
    tryGetProduct: (barcode: String) -> Unit,
    setBarcode: (barcode: String) -> Unit,
    seenProducts: List<ProductModel>
) {
    Column {
        BarcodeEntry(
            barcode = queryBarcode,
            getProduct = { tryGetProduct(queryBarcode) },
            setBarcode = setBarcode
        )

        Spacer(modifier = Modifier.height(8.dp))

        ProductWall(
            products = seenProducts,
            onProductClick = { tryGetProduct(it) },
            selectedBarcode = selectedBarcode
        )
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
        ProductPane(
            onDismiss = onDismiss,
            product = product
        )
    }
}


@Composable
fun ProductPanePlaceholder(isLoading: Boolean) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .width(150.dp),
                painter = painterResource(R.drawable.fay_logo),
                contentDescription = stringResource(R.string.fay_logo)
            )

            Text(
                text = "Enter a barcode, or choose an already seen product to view details...",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProductPane(
    onDismiss: () -> Unit,
    product: ProductModel
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    stringResource(R.string.product_info),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                ActionText(
                    "Done",
                    style = MaterialTheme.typography.titleMedium
                ) { onDismiss() }
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

@Composable
fun EcoGrade(ecoGrade: String?) {
    val ecoGrade = if ((ecoGrade?.length ?: 0) > 1) null else ecoGrade
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
            val grade = ecoGrade?.uppercase(getDefault()) ?: "?"
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
            modifier = Modifier.fillMaxWidth(),
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductWall(
    products: List<ProductModel>,
    onProductClick: (barcode: String) -> Unit,
    selectedBarcode: String?
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center
    ) {
        products.forEach {
            val isSelected = selectedBarcode == it.barcode
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f)
            ) {
                Box(modifier = Modifier.clickable { onProductClick(it.barcode) }) {
                    AsyncImage(
                        model =
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(it.image)
                                //.placeholder(R.drawable.generic_icon)
                                .build(),
                        contentDescription = it.name,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(FayRoundedCorner)
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Outline,
                                shape = FayRoundedCorner
                            ),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}




