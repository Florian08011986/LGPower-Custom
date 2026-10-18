package com.nic.lgpower.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OmaHeidiRemoteScreen(
    onSendKey: (String) -> Unit,
    onPowerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Oma Heidis Fernbedienung",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            Button(
                onClick = onPowerClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935),
                    contentColor = Color.White
                ),
                modifier = Modifier.size(76.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PowerSettingsNew,
                    contentDescription = "Ein / Aus",
                    modifier = Modifier.size(38.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                VerticalRocker(
                    topIcon = Icons.Default.KeyboardArrowUp,
                    topDesc = "Kanal hoch",
                    bottomIcon = Icons.Default.KeyboardArrowDown,
                    bottomDesc = "Kanal runter",
                    label = "Kanal",
                    onTopClick = { onSendKey("CHANNELUP") },
                    onBottomClick = { onSendKey("CHANNELDOWN") }
                )

                DpadSurface(
                    onUp = { onSendKey("UP") },
                    onDown = { onSendKey("DOWN") },
                    onLeft = { onSendKey("LEFT") },
                    onRight = { onSendKey("RIGHT") },
                    onOk = { onSendKey("ENTER") }
                )

                VerticalRocker(
                    topIcon = Icons.Default.VolumeUp,
                    topDesc = "Lauter",
                    bottomIcon = Icons.Default.VolumeDown,
                    bottomDesc = "Leiser",
                    label = "Lautstärke",
                    onTopClick = { onSendKey("VOLUMEUP") },
                    onBottomClick = { onSendKey("VOLUMEDOWN") }
                )
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(0.7f),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            NumberPad(
                onNumberClick = { digit -> onSendKey(digit.toString()) },
                onBackClick = { onSendKey("BACK") }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun VerticalRocker(
    topIcon: ImageVector,
    topDesc: String,
    bottomIcon: ImageVector,
    bottomDesc: String,
    label: String,
    onTopClick: () -> Unit,
    onBottomClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Surface(
            shape = RoundedCornerShape(34.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .width(64.dp)
                .height(170.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 10.dp)
            ) {
                IconButton(
                    onClick = onTopClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(topIcon, contentDescription = topDesc, modifier = Modifier.size(30.dp))
                }
                HorizontalDivider(modifier = Modifier.width(34.dp))
                IconButton(
                    onClick = onBottomClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(bottomIcon, contentDescription = bottomDesc, modifier = Modifier.size(30.dp))
                }
            }
        }
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun DpadSurface(
    onUp: () -> Unit,
    onDown: () -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit,
    onOk: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(170.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onUp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 6.dp)
                        .size(44.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Hoch", modifier = Modifier.size(30.dp))
                }
                IconButton(
                    onClick = onDown,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 6.dp)
                        .size(44.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Runter", modifier = Modifier.size(30.dp))
                }
                IconButton(
                    onClick = onLeft,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 6.dp)
                        .size(44.dp)
                ) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Links", modifier = Modifier.size(30.dp))
                }
                IconButton(
                    onClick = onRight,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 6.dp)
                        .size(44.dp)
                ) {
                    Icon(Icons.Default.ChevronRight, contentDescription = "Rechts", modifier = Modifier.size(30.dp))
                }
                FilledTonalButton(
                    onClick = onOk,
                    shape = CircleShape,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier.size(70.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
        Text(
            text = "Auswahl",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
