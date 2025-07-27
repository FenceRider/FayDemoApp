package com.example.faydemo.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.faydemo.R
import com.example.faydemo.ui.components.ActionText
import com.example.faydemo.ui.components.FayFullWidthButton
import com.example.faydemo.ui.components.FayFullWidthOutlineButton
import com.example.faydemo.ui.components.HorizontalDividerWithText


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
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {


            Image(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .width(150.dp),
                painter = painterResource(R.drawable.fay_logo),
                contentDescription = stringResource(R.string.fay_logo)
            )



            LoginSection(
                username = state.username,
                setUsername = viewModel::setUsername,
                password = state.password,
                setPassword = viewModel::setPassword,
                onLogin = viewModel::login,
            )

            Spacer(Modifier.weight(1f))

            ActionSection(onLogin = viewModel::login)
        }

    }

}





@Composable
fun ActionSection(
    onLogin: () -> Unit,
) {
    Column {
        FayFullWidthButton(
            onClick = onLogin,
            text = stringResource(R.string.continue_)
        )

        HorizontalDividerWithText(stringResource(R.string.or))

        FayFullWidthOutlineButton(
            onClick = onLogin,
            text = stringResource(R.string.log_in_with_email),
            icon = Icons.Default.Key
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            ActionText(
                text = stringResource(R.string.new_to_fay),
                action = {}
            )
        }

    }
}

@Composable
fun LoginSection(
    onLogin: () -> Unit,
    username: String,
    password: String,
    setUsername: (newValue: String) -> Unit,
    setPassword: (newValue: String) -> Unit
) {
    Column(
    ) {
        Text(
            stringResource(R.string.log_in),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.phone_number),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.enter_phone_number)) },
            value = username,
            onValueChange = setUsername,
            shape = RoundedCornerShape(10.dp)

        )
    }
}