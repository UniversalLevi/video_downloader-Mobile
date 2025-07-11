package com.example.hehedownloader

import android.content.Context
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class DownloadViewModel : ViewModel() {
    
    private val _downloadState = MutableStateFlow(DownloadState())
    val downloadState: StateFlow<DownloadState> = _downloadState.asStateFlow()
    
    fun getVideoInfo(url: String) {
        viewModelScope.launch {
            try {
                _downloadState.value = _downloadState.value.copy(
                    isLoading = true,
                    error = null
                )
                
                // Simulate network delay
                kotlinx.coroutines.delay(2000)
                
                // For now, create mock video info
                val videoInfo = VideoInfo(
                    title = "Sample Video - $url",
                    duration = 180L, // 3 minutes
                    uploader = "Sample Channel",
                    viewCount = 1000L,
                    thumbnail = "",
                    formats = 5
                )
                
                _downloadState.value = _downloadState.value.copy(
                    isLoading = false,
                    videoInfo = videoInfo
                )
                
            } catch (e: Exception) {
                _downloadState.value = _downloadState.value.copy(
                    isLoading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }
    
    fun downloadVideo(url: String) {
        viewModelScope.launch {
            try {
                _downloadState.value = _downloadState.value.copy(
                    isDownloading = true,
                    error = null,
                    downloadComplete = false
                )
                
                // Simulate download progress
                for (i in 1..10) {
                    kotlinx.coroutines.delay(500)
                    _downloadState.value = _downloadState.value.copy(
                        progress = i / 10f
                    )
                }
                
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val mockFilePath = "${downloadsDir.absolutePath}/sample_video.mp4"
                
                _downloadState.value = _downloadState.value.copy(
                    isDownloading = false,
                    downloadComplete = true,
                    downloadedFilePath = mockFilePath
                )
                
            } catch (e: Exception) {
                _downloadState.value = _downloadState.value.copy(
                    isDownloading = false,
                    error = "Download error: ${e.message}"
                )
            }
        }
    }
    
    fun clearError() {
        _downloadState.value = _downloadState.value.copy(error = null)
    }
    
    fun resetState() {
        _downloadState.value = DownloadState()
    }
} 