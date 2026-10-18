package com.nic.lgpower

import android.app.ActivityManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.hardware.ConsumerIrManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.HapticFeedbackConstants
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.nic.lgpower.ui.OmaHeidiRemoteScreen
import com.nic.lgpower.ui.theme.LGPowerTheme
import java.util.concurrent.atomic.AtomicBoolean

class MainActivity : AppCompatActivity() {

    private val client by lazy { WebOsClient(this) }
    private val statusHandler = Handler(Looper.getMainLooper())
    
    @Volatile private var tvConnected = false
    @Volatile private var wakeHomeGen = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Erstes Starten: Wenn keine IP hinterlegt ist, zum Setup wechseln
        if (client.tvIp.isBlank()) {
            getSharedPreferences("webos", MODE_PRIVATE).edit().putInt("last_seen_version", BuildConfig.VERSION_CODE).apply()
            startActivity(android.content.Intent(this, SetupActivity::class.java))
            finish()
            return
        }

        // Icon für den Task-Switcher anpassen (Transparentes Icon)
        val iconDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_launcher_foreground)
        val iconBmp = Bitmap.createBitmap(192, 192, Bitmap.Config.ARGB_8888)
        iconDrawable?.setBounds(0, 0, 192, 192)
        val iconCanvas = Canvas(iconBmp)
        iconCanvas.scale(1.5f, 1.5f, 96f, 96f)
        iconDrawable?.draw(iconCanvas)
        @Suppress("DEPRECATION")
        setTaskDescription(ActivityManager.TaskDescription(getString(R.string.app_name), iconBmp, Color.TRANSPARENT))

        // Setze das Jetpack Compose UI
        setContent {
            LGPowerTheme {
                OmaHeidiRemoteScreen(
                    onSendKey = { key -> handleTvKey(key) },
                    onPowerClick = { handlePowerClick() }
                )
            }
        }
        
        // Initialer Verbindungscheck
        checkConnectionStatus()
    }
    
    private fun handlePowerClick() {
        val view = window.decorView.rootView
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        
        // IR-Blaster als Fallback versuchen
        val irManager = getSystemService(CONSUMER_IR_SERVICE) as? ConsumerIrManager
        if (irManager?.hasIrEmitter() == true) {
            runCatching { irManager.transmit(38000, LGPowerWidget.LG_POWER_PATTERN) }
        }
        
        // WiFi / WebOS Power-Kommando
        val gen = ++wakeHomeGen
        Thread {
            if (tvIsOn()) {
                client.turnOff()
            } else {
                wakeTv(gen) { client.afterWake() }
            }
        }.start()
    }
    
    private fun handleTvKey(key: String) {
        val view = window.decorView.rootView
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        
        Thread {
            when (key) {
                "CHANNELUP" -> client.channelUp()
                "CHANNELDOWN" -> client.channelDown()
                "VOLUMEUP" -> client.volumeUp()
                "VOLUMEDOWN" -> client.volumeDown()
                "UP" -> client.pressUp()
                "DOWN" -> client.pressDown()
                "LEFT" -> client.pressLeft()
                "RIGHT" -> client.pressRight()
                "ENTER" -> client.pressEnter()
                "BACK" -> client.pressKey("BACK")
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> client.pressKey(key)
                else -> client.pressKey(key)
            }
        }.start()
    }
    
    private fun checkConnectionStatus() {
        // Simpler Status-Check aus dem Original
        Thread {
            val isOn = tvIsOn()
            runOnUiThread {
                tvConnected = isOn
            }
        }.start()
    }
    
    // Hilfsfunktionen (Original-Logik)
    private fun tvIsOn(): Boolean {
        var ret = false
        val t = Thread { ret = client.checkConnection() }
        t.start()
        t.join(800)
        return ret
    }

    private fun wakeTv(gen: Int, onAwake: () -> Unit) {
        client.wakeOnLan()
        for (i in 0..15) {
            if (wakeHomeGen != gen) return
            if (tvIsOn()) {
                runOnUiThread { onAwake() }
                return
            }
            Thread.sleep(if (i < 5) 500 else 1000)
        }
    }
}
