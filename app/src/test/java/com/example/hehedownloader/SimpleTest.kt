package com.example.hehedownloader

import org.junit.Test
import org.junit.Assert.*

class SimpleTest {
    @Test
    fun testBasicCompilation() {
        // Test that our basic classes can be instantiated
        val downloadState = DownloadState()
        assertNotNull(downloadState)
        
        val videoInfo = VideoInfo(
            title = "Test",
            duration = 100L,
            uploader = "Test",
            viewCount = 1000L,
            thumbnail = "",
            formats = 1
        )
        assertNotNull(videoInfo)
        
        // Test that we can access our utility classes
        assertNotNull(PermissionHandler)
        assertNotNull(ClipboardManager)
    }
} 