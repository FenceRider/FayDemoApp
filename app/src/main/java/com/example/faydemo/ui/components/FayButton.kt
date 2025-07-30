package com.example.faydemo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FayPrimaryButton(
    modifier: Modifier = Modifier,
    internalPadding: Dp = 8.dp,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = FayRoundedCorner
    ) {
        Box(modifier = Modifier.padding(vertical = internalPadding)) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimary) {
                content()
            }
        }
    }
}

@Composable
fun FayIconButton(
    modifier: Modifier = Modifier,
    internalPadding: Dp = 8.dp,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {

    val click = {
        if (!isLoading)
            onClick()
    }

    Surface(
        modifier = modifier,
        shape = FayRoundedCorner,
        color = MaterialTheme.colorScheme.primary,
        tonalElevation = 4.dp,
        enabled = !isLoading,
        onClick = click
    ) {
        IconButton(
            onClick = click,
            enabled = !isLoading,
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
        ) {
            Box(modifier = Modifier.padding(vertical = internalPadding)) {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimary) {
                    if (isLoading) {
                        Box(modifier = Modifier.padding(internalPadding)) {
                            LoadingSpinner()
                        }
                    } else {
                        content()
                    }
                }

            }
        }
    }
}