package com.nic.lgpower.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberPad(
    onNumberClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            NumberPadButton(1, onNumberClick)
            NumberPadButton(2, onNumberClick)
            NumberPadButton(3, onNumberClick)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            NumberPadButton(4, onNumberClick)
            NumberPadButton(5, onNumberClick)
            NumberPadButton(6, onNumberClick)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            NumberPadButton(7, onNumberClick)
            NumberPadButton(8, onNumberClick)
            NumberPadButton(9, onNumberClick)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalIconButton(
                onClick = onBackClick,
                shape = CircleShape,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Zurück",
                    modifier = Modifier.size(28.dp)
                )
            }
            NumberPadButton(0, onNumberClick)
            Spacer(modifier = Modifier.size(64.dp))
        }
    }
}

@Composable
private fun NumberPadButton(
    digit: Int,
    onClick: (Int) -> Unit
) {
    FilledTonalButton(
        onClick = { onClick(digit) },
        shape = CircleShape,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.size(64.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = digit.toString(),
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
