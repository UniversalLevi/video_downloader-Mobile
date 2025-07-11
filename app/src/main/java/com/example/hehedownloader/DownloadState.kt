package com.example.hehedownloader

data class DownloadState(
    val isLoading: Boolean = false,
    val isDownloading: Boolean = false,
    val progress: Float = 0f,
    val videoInfo: VideoInfo? = null,
    val error: String? = null,
    val downloadComplete: Boolean = false,
    val downloadedFilePath: String? = null
)

data class VideoInfo(
    val title: String,
    val duration: Long,
    val uploader: String,
    val viewCount: Long,
    val thumbnail: String,
    val formats: Int
)

sealed class DownloadResult {
    data class Success(
        val filePath: String,
        val title: String,
        val duration: Long,
        val fileSize: Long
    ) : DownloadResult()
    
    data class Error(val message: String) : DownloadResult()
} 