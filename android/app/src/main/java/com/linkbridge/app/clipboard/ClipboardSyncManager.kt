package com.linkbridge.app.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipboardSyncManager(private val context: Context) {
    private val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    fun setClipboardText(text: String) {
        val clip = ClipData.newPlainText("LinkBridge", text)
        clipboard.setPrimaryClip(clip)
    }

    fun getClipboardText(): String? {
        if (clipboard.hasPrimaryClip() && (clipboard.primaryClip?.itemCount ?: 0) > 0) {
            return clipboard.primaryClip?.getItemAt(0)?.text?.toString()
        }
        return null
    }
}
