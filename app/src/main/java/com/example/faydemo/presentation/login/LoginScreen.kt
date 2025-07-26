package com.example.faydemo.presentation.login

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import java.nio.file.WatchEvent


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    successfulLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    var initialized by rememberSaveable { mutableStateOf(false) }

    if (!initialized) {
        LaunchedEffect(Unit) {
            viewModel.init()
            initialized = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { navEvent ->
            when (navEvent) {
                LoginNavEvent.SuccessfulLogin -> successfulLogin()
            }
        }
    }

    Box(
        modifier = Modifier.padding(innerPadding)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("This is the Login Screen")

            Button(
                onClick = {
                    viewModel.login()
                }
            ) {
                Text("Login")
            }
        }

    }

}