package com.example.scanqr.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scanqr.utils.ClipboardHelper

/**
 * 扫码结果展示界面
 * 显示扫描到的二维码内容，并提供复制功能
 */
@Composable
fun ResultScreen(
    result: String,
    onCopy: () -> Unit,
    onScanAgain: () -> Unit
) {
    val context = LocalContext.current
    var showCopiedMessage by remember { mutableStateOf(false) }

    // 日志：记录收到的结果
    LaunchedEffect(result) {
        Log.d("ResultScreen", "Received result (length: ${result.length}): ${result.take(100)}...")
    }

    // 自动隐藏复制成功提示
    LaunchedEffect(showCopiedMessage) {
        if (showCopiedMessage) {
            kotlinx.coroutines.delay(2000)
            showCopiedMessage = false
        }
    }

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 标题
            Text(
                text = "扫描成功",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // 显示内容长度
            Text(
                text = "内容长度: ${result.length} 字符",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 二维码内容卡片
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "二维码内容",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        text = if (result.isNotEmpty()) result else "(无内容)",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp
                        ),
                        color = if (result.isNotEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    )
                }
            }

            // 复制按钮和继续扫码按钮
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 复制按钮 - 左侧
                Button(
                    onClick = {
                        Log.d("ResultScreen", "Copying text (length: ${result.length})")
                        try {
                            ClipboardHelper.copyText(context, result)
                            onCopy()
                            showCopiedMessage = true
                            Log.d("ResultScreen", "Copy successful")
                        } catch (e: Exception) {
                            Log.e("ResultScreen", "Copy failed", e)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ContentCopy,
                        contentDescription = "复制",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("复制")
                }

                // 继续扫码按钮 - 右侧
                Button(
                    onClick = onScanAgain,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "继续扫码",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("继续扫码")
                }
            }

            // 复制成功提示
            if (showCopiedMessage) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        color = Color(0xFF4CAF50),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(horizontal = 32.dp)
                    ) {
                        Text(
                            text = "已复制到剪贴板",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
