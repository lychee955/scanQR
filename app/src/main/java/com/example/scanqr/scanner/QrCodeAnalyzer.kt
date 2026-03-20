package com.example.scanqr.scanner

import android.annotation.SuppressLint
import android.graphics.PointF
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

/**
 * ML Kit 二维码分析器
 * 用于实时分析相机图像中的二维码
 * 返回二维码信息和位置
 */
class QrCodeAnalyzer(
    private val onQRCodeDetected: (List<QRCodeInfo>) -> Unit
) : ImageAnalysis.Analyzer {

    private val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .enableAllPotentialBarcodes()
            .build()
    )

    private var lastDetectionTime = 0L
    private val minDetectionInterval = 500L

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastDetectionTime < minDetectionInterval) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            val imageWidth = mediaImage.width
            val imageHeight = mediaImage.height
            val rotation = imageProxy.imageInfo.rotationDegrees

            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    val qrCodeList = mutableListOf<QRCodeInfo>()

                    for (barcode in barcodes) {
                        val content = barcode.rawValue
                        val boundingBox = barcode.boundingBox
                        val cornerPoints = barcode.cornerPoints

                        if (content != null && content.isNotEmpty() && boundingBox != null) {
                            val rectF = android.graphics.RectF(
                                boundingBox.left.toFloat(),
                                boundingBox.top.toFloat(),
                                boundingBox.right.toFloat(),
                                boundingBox.bottom.toFloat()
                            )

                            qrCodeList.add(
                                QRCodeInfo(
                                    content = content,
                                    boundingBox = rectF,
                                    corners = cornerPoints?.map { PointF(it.x.toFloat(), it.y.toFloat()) },
                                    imageWidth = imageWidth,
                                    imageHeight = imageHeight,
                                    rotationDegrees = rotation
                                )
                            )
                        }
                    }

                    if (qrCodeList.isNotEmpty()) {
                        lastDetectionTime = currentTime
                        Log.d("QrCodeAnalyzer", "Detected ${qrCodeList.size} QR code(s), image: ${imageWidth}x${imageHeight}, rotation: $rotation")
                        onQRCodeDetected(qrCodeList)
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("QrCodeAnalyzer", "Detection failed", e)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }

    fun release() {
        barcodeScanner.close()
    }
}
