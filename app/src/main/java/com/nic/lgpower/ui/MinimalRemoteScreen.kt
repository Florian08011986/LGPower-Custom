package com.nic.lgpower.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MinimalRemoteScreen(
    isConnected: Boolean,
    onSendCommand: (String) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    var warningDismissedManually by remember { mutableStateOf(false) }

    LaunchedEffect(isConnected) {
        if (isConnected) {
            warningDismissedManually = false
        }
    }

    val backgroundColor = Color(0xFF121212)
    val surfaceColor = Color(0xFF1E1E1E)
    val accentRed = Color(0xFFE53935)
    val textPrimary = Color(0xFFEEEEEE)
    val textSecondary = Color(0xFF9E9E9E)

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(surfaceColor)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true, color = accentRed)
                        ) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onSendCommand("POWER")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "⏻",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentRed
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .width(76.dp)
                            .clip(RoundedCornerShape(38.dp))
                            .background(surfaceColor)
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = true)
                                ) {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onSendCommand("VOLUME_UP")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "+", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = textPrimary)
                        }

                        Text(
                            text = "VOL",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = textSecondary,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = true)
                                ) {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onSendCommand("VOLUME_DOWN")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "−", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = textPrimary)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .width(76.dp)
                            .clip(RoundedCornerShape(38.dp))
                            .background(surfaceColor)
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = true)
                                ) {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onSendCommand("CHANNEL_UP")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "∧", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textPrimary)
                        }

                        Text(
                            text = "CH",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = textSecondary,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = true)
                                ) {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onSendCommand("CHANNEL_DOWN")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "∨", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textPrimary)
                        }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val numRows = listOf(
                        listOf("1", "2", "3"),
                        listOf("4", "5", "6"),
                        listOf("7", "8", "9")
                    )

                    for (row in numRows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (digit in row) {
                                RemoteRoundButton(
                                    label = digit,
                                    surfaceColor = surfaceColor,
                                    textColor = textPrimary
                                ) {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onSendCommand(digit)
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        RemoteRoundButton(
                            label = "🔇",
                            surfaceColor = surfaceColor,
                            textColor = textPrimary
                        ) {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onSendCommand("MUTE")
                        }

                        RemoteRoundButton(
                            label = "0",
                            surfaceColor = surfaceColor,
                            textColor = textPrimary
                        ) {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onSendCommand("0")
                        }

                        RemoteRoundButton(
                            label = "OK",
                            surfaceColor = surfaceColor,
                            textColor = textPrimary
                        ) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onSendCommand("OK")
                        }
                    }
                }
            }
        }

        val showWarning = !isConnected && !warningDismissedManually
        AnimatedVisibility(
            visible = showWarning,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xF50A0A0A))
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "⚠️",
                        fontSize = 54.sp
                    )

                    Text(
                        text = "Keine Verbindung zum TV",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )

                    Text(
                        text = "Der Fernseher ist offline oder nicht erreichbar. Prüfe, ob das Gerät eingeschaltet und im selben WLAN angemeldet ist.",
                        fontSize = 14.sp,
                        color = Color(0xFFAAAAAA),
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(bottom = 28.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(surfaceColor)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true, color = accentRed)
                            ) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                onSendCommand("POWER")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "⏻",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = accentRed
                        )
                    }

                    Text(
                        text = "Einschalten (WOL)",
                        fontSize = 12.sp,
                        color = textSecondary,
                        modifier = Modifier.padding(top = 8.dp, bottom = 28.dp)
                    )

                    Button(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onSendCommand("RECONNECT")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {
                        Text(text = "Verbindung wiederholen", color = Color.White)
                    }

                    OutlinedButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            warningDismissedManually = true
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .padding(top = 10.dp)
                    ) {
                        Text(text = "Trotzdem zur Fernbedienung", color = Color(0xFF888888))
                    }
                }
            }
        }
    }
}

@Composable
private fun RemoteRoundButton(
    label: String,
    surfaceColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(68.dp)
            .clip(CircleShape)
            .background(surfaceColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}
