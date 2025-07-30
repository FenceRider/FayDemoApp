package com.example.faydemo.presentation.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.faydemo.R
import com.example.faydemo.ui.components.ActionText
import com.example.faydemo.ui.components.FayFullWidthButton
import com.example.faydemo.ui.components.FayFullWidthOutlineButton
import com.example.faydemo.ui.components.FayOutlinedTextField
import com.example.faydemo.ui.components.HorizontalDividerWithText
import com.example.faydemo.ui.components.ScreenPadding


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

    ScreenPadding(innerPadding) {

        val configuration = LocalConfiguration.current

        when (configuration.orientation) {

            Configuration.ORIENTATION_LANDSCAPE -> {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .width(150.dp),
                        painter = painterResource(R.drawable.fay_logo),
                        contentDescription = stringResource(R.string.fay_logo)
                    )

                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(.5f)
                        ) {

                            Spacer(modifier = Modifier.height(12.dp))
                            LoginSection(
                                phoneNumber = state.phoneNumber,
                                setPhoneNumber = viewModel::setUsername,
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Column(
                            modifier = Modifier
                                .weight(.5f)

                        ) {
                            ActionSection(onLogin = viewModel::login)
                        }
                    }
                }
            }

            else -> {
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
                        phoneNumber = state.phoneNumber,
                        setPhoneNumber = viewModel::setUsername,
                    )

                    Spacer(Modifier.weight(1f))

                    ActionSection(onLogin = viewModel::login)
                }
            }
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
    phoneNumber: String,
    setPhoneNumber: (newValue: String) -> Unit,
) {
    Column(
    ) {
        Text(
            stringResource(R.string.log_in),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = stringResource(R.string.phone_number),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        FayOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.enter_phone_number)) },
            value = phoneNumber,
            onValueChange = setPhoneNumber
        )
    }
}