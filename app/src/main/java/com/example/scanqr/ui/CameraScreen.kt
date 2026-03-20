package com.example.scanqr.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.scanqr.scanner.QRCodeInfo

/**
 * 扫码界面
 * 显示相机预览和二维码列表
 */
@Composable
fun CameraScreen(
    onScanResult: (String) -> Unit
) {
    var qrCodes by remember { mutableStateOf<List<QRCodeInfo>>(emptyList()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 相机预览
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onQRCodeDetected = { detectedQRs ->
                    qrCodes = detectedQRs
                },
                onQRCodeSelected = { content ->
                    onScanResult(content)
                }
            )

            // 顶部提示
            if (qrCodes.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 48.dp)
                        .background(
                            color = Color(0xFF00FF00).copy(alpha = 0.8f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "检测到 ${qrCodes.size} 个二维码，点击下方列表选择",
                        color = Color.Black,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 48.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "将二维码放入取景框内",
                        color = Color.White,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // 底部二维码列表
            if (qrCodes.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(qrCodes) { qrCode ->
                            QRCodeItem(
                                qrCode = qrCode,
                                onClick = { onScanResult(qrCode.content) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QRCodeItem(
    qrCode: QRCodeInfo,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = Color(0xFF00FF00).copy(alpha = 0.2f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = qrCode.content,
                color = Color.White,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
