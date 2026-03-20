package com.example.scanqr.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * 剪贴板工具类
 * 用于快速复制文本到系统剪贴板
 */
object ClipboardHelper {

    /**
     * 复制文本到剪贴板
     * @param context 上下文
     * @param text 要复制的文本
     * @param label 剪贴板标签 (可选，用于标识数据源)
     */
    fun copyText(context: Context, text: String, label: String = "QR Code") {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(label, text)
        clipboardManager.setPrimaryClip(clipData)
    }

    /**
     * 获取剪贴板中的文本
     * @param context 上下文
     * @return 剪贴板中的文本，如果没有则返回 null
     */
    fun getText(context: Context): String? {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = clipboardManager.primaryClip
        return if (clipData != null && clipData.itemCount > 0) {
            clipData.getItemAt(0).text?.toString()
        } else {
            null
        }
    }
}
