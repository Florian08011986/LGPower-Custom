#!/data/data/com.termux/files/usr/bin/bash
set -e

echo "[1/4] Verifiziere Repository-Pfad..."
cd ~/projects/LGPower-Custom

echo "[2/4] Aktualisiere App-Name in Ressourcen..."
if [ -f "app/src/main/res/values/strings.xml" ]; then
    sed -i 's/<string name="app_name">.*<\/string>/<string name="app_name">Oma Heidis Fernbedienung<\/string>/g' app/src/main/res/values/strings.xml
fi

mkdir -p app/src/main/java/com/nic/lgpower/ui

echo "[3/4] Schreibe Jetpack Compose Komponenten..."

# 1. Ziffernblock
cat << 'KOTLIN_PAD' > app/src/main/java/com/nic/lgpower/ui/NumberPad.kt
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
KOTLIN_PAD

# 2. Oma Heidi Remote Hauptscreen
cat << 'KOTLIN_SCREEN' > app/src/main/java/com/nic/lgpower/ui/OmaHeidiRemoteScreen.kt
package com.nic.lgpower.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
                        style = MaterialTheme.typography.titleLarge
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
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Button(
                onClick = onPowerClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935),
                    contentColor = Color.White
                ),
                modifier = Modifier.size(72.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PowerSettingsNew,
                    contentDescription = "Ein / Aus",
                    modifier = Modifier.size(36.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                VerticalRocker(
                    topIcon = Icons.Default.KeyboardArrowUp,
                    topDesc = "Kanal hoch",
                    bottomIcon = Icons.Default.KeyboardArrowDown,
                    bottomDesc = "Kanal runter",
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
                    onTopClick = { onSendKey("VOLUMEUP") },
                    onBottomClick = { onSendKey("VOLUMEDOWN") }
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            NumberPad(
                onNumberClick = { digit -> onSendKey(digit.toString()) },
                onBackClick = { onSendKey("BACK") }
            )
        }
    }
}

@Composable
fun VerticalRocker(
    topIcon: ImageVector,
    topDesc: String,
    bottomIcon: ImageVector,
    bottomDesc: String,
    onTopClick: () -> Unit,
    onBottomClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .width(60.dp)
            .height(165.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 8.dp)
        ) {
            IconButton(
                onClick = onTopClick,
                modifier = Modifier.size(46.dp)
            ) {
                Icon(topIcon, contentDescription = topDesc, modifier = Modifier.size(28.dp))
            }
            HorizontalDivider(modifier = Modifier.width(32.dp))
            IconButton(
                onClick = onBottomClick,
                modifier = Modifier.size(46.dp)
            ) {
                Icon(bottomIcon, contentDescription = bottomDesc, modifier = Modifier.size(28.dp))
            }
        }
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
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.size(170.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onUp,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 2.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Hoch", modifier = Modifier.size(32.dp))
            }
            IconButton(
                onClick = onDown,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 2.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Runter", modifier = Modifier.size(32.dp))
            }
            IconButton(
                onClick = onLeft,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 2.dp)
            ) {
                Text("◀", fontSize = 18.sp)
            }
            IconButton(
                onClick = onRight,
                modifier = Modifier.align(Alignment.CenterEnd).padding(end = 2.dp)
            ) {
                Text("▶", fontSize = 18.sp)
            }
            FilledTonalButton(
                onClick = onOk,
                shape = CircleShape,
                modifier = Modifier.size(64.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("OK", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }
        }
    }
}
KOTLIN_SCREEN

echo "[4/4] Versioniere und synchronisiere mit GitHub..."
git add app/src/main/res/values/strings.xml app/src/main/java/com/nic/lgpower/ui/NumberPad.kt app/src/main/java/com/nic/lgpower/ui/OmaHeidiRemoteScreen.kt setup_oma_heidi.sh
git commit -m "feat(ui): Umbau auf Oma Heidis Fernbedienung, CH-Wippe und Ziffernblock"
git push origin main

STATUS_MSG="Erfolgreich ausgeführt: UI-Anpassungen integriert und nach git@github.com:Florian08011986/LGPower-Custom.git gepusht."
echo "$STATUS_MSG"

if command -v termux-clipboard-set >/dev/null 2>&1; then
    echo "$STATUS_MSG" | termux-clipboard-set
fi
