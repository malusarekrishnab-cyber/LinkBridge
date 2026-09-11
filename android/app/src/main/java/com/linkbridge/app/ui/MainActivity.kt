package com.linkbridge.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linkbridge.app.connection.DiscoveryManager

class MainActivity : ComponentActivity() {
    private lateinit var discoveryManager: DiscoveryManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        discoveryManager = DiscoveryManager(this)
        discoveryManager.startAdvertising(5555)

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFF00BCD4),
                    background = Color(0xFF1E1E1E)
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LinkBridgeScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        discoveryManager.stopAdvertising()
    }
}

@Composable
fun LinkBridgeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "LINKBRIDGE",
            color = Color(0xFF00BCD4),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Device: My Phone",
            color = Color.LightGray,
            fontSize = 18.sp
        )
        Text(
            text = "Status: Waiting for connection...",
            color = Color.LightGray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { /* TODO: Open QR Code Scanner */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
        ) {
            Text("Scan QR Code", color = Color.White)
        }
    }
}
