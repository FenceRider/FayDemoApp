package com.example.faydemo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ActionText(
    text: String,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    action: () -> Unit
) {
    Text(
        modifier = Modifier.clickable { action() },
        text = text,
        fontWeight = FontWeight.Bold,
        style = style,
        color = MaterialTheme.colorScheme.primary
    )
}