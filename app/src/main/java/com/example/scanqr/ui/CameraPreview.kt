package com.example.scanqr.ui

import android.util.Log
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.example.scanqr.scanner.CameraManager
import com.example.scanqr.scanner.QRCodeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 相机预览组件
 */
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onQRCodeDetected: (List<QRCodeInfo>) -> Unit,
    onQRCodeSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraManager = remember { CameraManager(context.applicationContext) }
    var isInitialized by remember { mutableStateOf(false) }

    var qrCodes by remember { mutableStateOf<List<QRCodeInfo>>(emptyList()) }
    var previewView by remember { mutableStateOf<PreviewView?>(null) }

    LaunchedEffect(Unit) {
        try {
            withContext(Dispatchers.Main) {
                cameraManager.initialize()
                isInitialized = true
            }
        } catch (e: Exception) {
            Log.e("CameraPreview", "Camera initialization failed", e)
        }
    }

    LaunchedEffect(qrCodes) {
        if (qrCodes.isNotEmpty()) {
            onQRCodeDetected(qrCodes)
        }
    }

    if (isInitialized) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        this.scaleType = PreviewView.ScaleType.FIT_CENTER
                        previewView = this
                    }
                },
                modifier = Modifier.fillMaxSize(),
                update = { view ->
                    previewView = view
                    try {
                        cameraManager.startCamera(
                            view,
                            lifecycleOwner
                        ) { detectedQRs ->
                            qrCodes = detectedQRs
                        }
                    } catch (e: Exception) {
                        Log.e("CameraPreview", "Camera start failed", e)
                    }
                }
            )
        }
    }
}
