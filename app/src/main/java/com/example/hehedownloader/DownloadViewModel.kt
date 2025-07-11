package com.example.hehedownloader

import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder

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
                kotlinx.coroutines.delay(500)
                val videoInfo = VideoInfo(
                    title = url,
                    url = url
                )
                _downloadState.value = _downloadState.value.copy(
                    isLoading = false,
                    videoInfo = videoInfo
                )
            } catch (e: Exception) {
                _downloadState.value = _downloadState.value.copy(
                    isLoading = false,
                    error = "Error: ${e.message ?: "Unknown error"}"
                )
            }
        }
    }

    fun downloadRealVideo(url: String) {
        viewModelScope.launch {
            try {
                _downloadState.value = _downloadState.value.copy(
                    isDownloading = true,
                    error = null,
                    downloadComplete = false
                )
                val downloadsDir = withContext(Dispatchers.IO) {
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).apply {
                        if (!exists()) mkdirs()
                    }
                }
                val fileName = extractFileNameFromUrl(url)
                val videoFile = File(downloadsDir, fileName)
                try {
                    withContext(Dispatchers.IO) {
                        var connection: HttpURLConnection? = null
                        var redirectCount = 0
                        var currentUrl = url
                        while (redirectCount < 5) {
                            connection = URL(currentUrl).openConnection() as HttpURLConnection
                            connection.connectTimeout = 15000
                            connection.readTimeout = 20000
                            connection.instanceFollowRedirects = false
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                            connection.setRequestProperty("Accept", "*/*")
                            connection.setRequestProperty("Connection", "keep-alive")
                            connection.connect()
                            val responseCode = connection.responseCode
                            if (responseCode in 300..399) {
                                val location = connection.getHeaderField("Location")
                                if (location != null) {
                                    currentUrl = location
                                    redirectCount++
                                    connection.disconnect()
                                    continue
                                } else {
                                    _downloadState.value = _downloadState.value.copy(
                                        isDownloading = false,
                                        error = "Redirected but no Location header found."
                                    )
                                    connection.disconnect()
                                    return@withContext
                                }
                            }
                            if (responseCode != HttpURLConnection.HTTP_OK) {
                                _downloadState.value = _downloadState.value.copy(
                                    isDownloading = false,
                                    error = "Failed to download: HTTP $responseCode ${connection.responseMessage}"
                                )
                                connection.disconnect()
                                return@withContext
                            }
                            break
                        }
                        if (connection == null) {
                            _downloadState.value = _downloadState.value.copy(
                                isDownloading = false,
                                error = "Failed to establish connection."
                            )
                            return@withContext
                        }
                        val contentLength = connection.contentLengthLong
                        val inputStream = connection.inputStream
                        val outputStream = FileOutputStream(videoFile)
                        val buffer = ByteArray(8192)
                        var bytesRead: Int
                        var totalBytesRead = 0L
                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                            totalBytesRead += bytesRead
                            val progress = if (contentLength > 0) totalBytesRead.toFloat() / contentLength else 0.5f
                            _downloadState.value = _downloadState.value.copy(progress = progress.coerceIn(0f, 1f))
                        }
                        inputStream.close()
                        outputStream.close()
                        connection.disconnect()

                        // Check if file is suspiciously small or contains HTML
                        if (videoFile.length() < 10_000) {
                            val content = videoFile.readText(Charsets.UTF_8)
                            if (content.contains("<html", ignoreCase = true) || content.contains("<!DOCTYPE html", ignoreCase = true)) {
                                videoFile.delete()
                                _downloadState.value = _downloadState.value.copy(
                                    isDownloading = false,
                                    error = "The link does not point to a real video file. Try a direct video link (ending in .mp4, .webm, etc.)."
                                )
                                return@withContext
                            }
                        }
                        _downloadState.value = _downloadState.value.copy(
                            isDownloading = false,
                            downloadComplete = true,
                            downloadedFilePath = videoFile.absolutePath
                        )
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.IO) { videoFile.delete() }
                    _downloadState.value = _downloadState.value.copy(
                        isDownloading = false,
                        error = "Failed to download video: ${e.message ?: e.javaClass.simpleName}"
                    )
                }
            } catch (e: Exception) {
                _downloadState.value = _downloadState.value.copy(
                    isDownloading = false,
                    error = "Download error: ${e.message ?: e.javaClass.simpleName}"
                )
            }
        }
    }

    private fun extractFileNameFromUrl(url: String): String {
        return try {
            val decodedUrl = URLDecoder.decode(url, "UTF-8")
            val lastSegment = decodedUrl.substringAfterLast('/')
            if (lastSegment.contains('?')) lastSegment.substringBefore('?') else lastSegment
        } catch (e: Exception) {
            "video_${System.currentTimeMillis()}.mp4"
        }
    }

    fun clearError() {
        _downloadState.value = _downloadState.value.copy(error = null)
    }
    fun resetState() {
        _downloadState.value = DownloadState()
    }
}

data class VideoInfo(
    val title: String,
    val url: String
)

data class DownloadState(
    val isLoading: Boolean = false,
    val isDownloading: Boolean = false,
    val progress: Float = 0f,
    val videoInfo: VideoInfo? = null,
    val error: String? = null,
    val downloadComplete: Boolean = false,
    val downloadedFilePath: String? = null
) 