package com.example.hehedownloader

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object ClipboardManager {
    
    fun getClipboardText(context: Context): String? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = clipboard.primaryClip
        
        return if (clipData != null && clipData.itemCount > 0) {
            clipData.getItemAt(0).text?.toString()
        } else {
            null
        }
    }
    
    fun setClipboardText(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("URL", text)
        clipboard.setPrimaryClip(clip)
    }
    
    fun hasClipboardText(context: Context): Boolean {
        return getClipboardText(context) != null
    }
} 