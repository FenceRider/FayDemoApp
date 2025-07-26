package com.example.faydemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.faydemo.navigation.FayDemoNavGraph
import com.example.faydemo.ui.theme.FayDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FayDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FayDemoApp(innerPadding = innerPadding)
                }
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun FayDemoApp(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        val routes = controller
            .currentBackStack.value.joinToString(", ") { it.destination.route ?: "" }

        Timber
            .tag("BackStackLog")
            .d("Current Route:${navController.currentDestination?.route}, BackStack: $routes")
    }
    //nav graph

    FayDemoNavGraph(
        navController = navController,
        innerPadding = innerPadding,
        modifier = Modifier,
    )
}