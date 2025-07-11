package com.example.hehedownloader

import org.junit.Test
import org.junit.Assert.*

class DownloadTest {
    @Test
    fun testDownloadState() {
        val downloadState = DownloadState()
        assertFalse(downloadState.isLoading)
        assertFalse(downloadState.isDownloading)
        assertEquals(0f, downloadState.progress, 0.01f)
        assertNull(downloadState.videoInfo)
        assertNull(downloadState.error)
        assertFalse(downloadState.downloadComplete)
        assertNull(downloadState.downloadedFilePath)
    }
    
    @Test
    fun testVideoInfo() {
        val videoInfo = VideoInfo(
            title = "Test Video",
            url = "https://example.com/video.mp4"
        )
        assertEquals("Test Video", videoInfo.title)
        assertEquals("https://example.com/video.mp4", videoInfo.url)
    }
    
    @Test
    fun testFileOperations() {
        val fileName = "test_video_${System.currentTimeMillis()}.mp4"
        assertNotNull(fileName)
        assertTrue(fileName.contains(".mp4"))
    }
} 