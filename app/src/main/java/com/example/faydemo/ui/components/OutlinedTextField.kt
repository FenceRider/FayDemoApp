package com.example.faydemo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import com.example.faydemo.R

@Composable
fun FayOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (newValue: String) -> Unit,
    placeholder: (@Composable () -> Unit)? = null
) {
    OutlinedTextField(
        modifier = modifier,
        placeholder = placeholder,
        value = value,
        onValueChange = onValueChange,
        shape = FayRoundedCorner
    )
}