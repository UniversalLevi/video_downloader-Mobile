package com.example.hehedownloader

import org.junit.Test
import org.junit.Assert.*

class SimpleTest {
    @Test
    fun testBasicCompilation() {
        val downloadState = DownloadState()
        assertNotNull(downloadState)
        val videoInfo = VideoInfo(
            title = "Test",
            url = "https://example.com/video.mp4"
        )
        assertNotNull(videoInfo)
        assertNotNull(PermissionHandler)
        assertNotNull(ClipboardManager)
    }
} 