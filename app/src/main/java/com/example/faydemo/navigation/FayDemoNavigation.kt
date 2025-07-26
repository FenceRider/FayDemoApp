package com.example.faydemo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.faydemo.presentation.login.LoginScreen
import com.example.faydemo.presentation.product.ProductScreen
import kotlinx.serialization.Serializable


@Serializable
data object LoginRoute

@Serializable
data object ProductRoute

@Composable
fun FayDemoNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    modifier: Modifier
) {

    NavHost(
        navController = navController,
        startDestination = LoginRoute,
        modifier = modifier
    ) {

        composable<LoginRoute> {
            LoginScreen(
                innerPadding = innerPadding,
                successfulLogin = {
                    navController.navigate(ProductRoute)
                }
            )
        }

        composable<ProductRoute> {
            ProductScreen(
                innerPadding = innerPadding)
        }
    }

}