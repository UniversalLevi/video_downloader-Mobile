package com.example.hehedownloader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.hehedownloader.ui.screens.DownloadScreen
import com.example.hehedownloader.ui.theme.HeheDownloaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HeheDownloaderTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DownloadScreen()
                }
            }
        }
    }
}