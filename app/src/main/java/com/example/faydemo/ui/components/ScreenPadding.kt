package com.example.faydemo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.faydemo.ui.theme.DefaultScreenPadding

@Composable
fun ScreenPadding(
    innerPadding: PaddingValues,
    screenPadding: Dp = DefaultScreenPadding,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .padding(screenPadding)

    ) {
        content()
    }

}