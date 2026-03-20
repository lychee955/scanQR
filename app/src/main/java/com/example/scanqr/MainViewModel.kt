package com.example.scanqr

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.scanqr.utils.ClipboardHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _copiedMessage = MutableStateFlow<String?>(null)
    val copiedMessage: StateFlow<String?> = _copiedMessage

    /**
     * 复制文本到剪贴板
     */
    fun copyToClipboard(context: Context, text: String) {
        ClipboardHelper.copyText(context, text)
        _copiedMessage.value = "已复制到剪贴板"
    }

    /**
     * 清除复制提示消息
     */
    fun clearCopiedMessage() {
        _copiedMessage.value = null
    }
}
