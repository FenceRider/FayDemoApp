package com.example.faydemo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ActionText(
    text: String,
    action: () -> Unit
) {
    Text(
        modifier = Modifier.clickable { action() },
        text = text,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary
    )
}