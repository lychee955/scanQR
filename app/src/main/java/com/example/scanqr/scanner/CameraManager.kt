package com.example.scanqr.scanner

import android.content.Context
import android.util.Log
import android.util.Size
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * 相机管理器
 * 负责初始化和管理 CameraX 相机
 */
class CameraManager(
    private val context: Context
) {

    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var camera: Camera
    private lateinit var preview: Preview
    private lateinit var imageAnalysis: ImageAnalysis
    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    /**
     * 初始化相机
     */
    suspend fun initialize() {
        cameraProvider = suspendCancellableCoroutine { cont ->
            val future = ProcessCameraProvider.getInstance(context)
            future.addListener(
                { cont.resume(future.get()) },
                cameraExecutor
            )
        }
    }

    /**
     * 启动相机
     */
    fun startCamera(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        onQRCodeDetected: (List<QRCodeInfo>) -> Unit
    ) {
        try {
            // 取消之前的绑定
            cameraProvider.unbindAll()

            // 预览 - 使用更高分辨率
            preview = Preview.Builder()
                .setTargetResolution(Size(1920, 1080))
                .build()
            preview.setSurfaceProvider(previewView.surfaceProvider)

            // 图像分析
            imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(1920, 1080))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(cameraExecutor, QrCodeAnalyzer(onQRCodeDetected))

            // 选择后置摄像头
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // 绑定到生命周期
            camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis
            )

            Log.d("CameraManager", "Camera started successfully")

        } catch (e: Exception) {
            Log.e("CameraManager", "Failed to start camera", e)
            e.printStackTrace()
        }
    }

    /**
     * 停止相机
     */
    fun stopCamera() {
        try {
            cameraProvider.unbindAll()
            Log.d("CameraManager", "Camera stopped")
        } catch (e: Exception) {
            Log.e("CameraManager", "Failed to stop camera", e)
        }
    }

    /**
     * 释放资源
     */
    fun release() {
        try {
            cameraExecutor.shutdown()
            Log.d("CameraManager", "Resources released")
        } catch (e: Exception) {
            Log.e("CameraManager", "Failed to release resources", e)
        }
    }

    /**
     * 检查设备是否有后置摄像头
     */
    fun hasBackCamera(): Boolean {
        return try {
            cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)
        } catch (e: Exception) {
            false
        }
    }
}
