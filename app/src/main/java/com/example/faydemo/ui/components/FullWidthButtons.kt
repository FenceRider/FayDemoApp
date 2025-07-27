package com.example.faydemo.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FayFullWidthButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun FayFullWidthOutlineButton(
    onClick: () -> Unit,
    text: String,
    icon: ImageVector? = null
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(10.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            icon?.let {
                Icon(
                    imageVector = icon,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface,

                )
        }

    }
}