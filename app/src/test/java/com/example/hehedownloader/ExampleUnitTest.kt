package com.example.hehedownloader

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testDownloadStateCreation() {
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
    fun testVideoInfoCreation() {
        val videoInfo = VideoInfo(
            title = "Test Video",
            url = "https://example.com/video.mp4"
        )
        assertEquals("Test Video", videoInfo.title)
        assertEquals("https://example.com/video.mp4", videoInfo.url)
    }
    
    @Test
    fun testBasicFunctionality() {
        // Test that our basic classes can be instantiated
        val permissionHandler = PermissionHandler
        val clipboardManager = ClipboardManager
        
        // These should not throw any exceptions
        assertNotNull(permissionHandler)
        assertNotNull(clipboardManager)
    }
}