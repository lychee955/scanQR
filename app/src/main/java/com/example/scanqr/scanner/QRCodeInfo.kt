package com.example.scanqr.scanner

import android.graphics.RectF

/**
 * 二维码信息
 * 包含识别到的内容和边界框位置
 */
data class QRCodeInfo(
    val content: String,
    val boundingBox: RectF,
    val corners: List<android.graphics.PointF>? = null,
    val imageWidth: Int = 0,
    val imageHeight: Int = 0,
    val rotationDegrees: Int = 0
) {
    /**
     * 检查点是否在二维码边界框内
     */
    fun contains(x: Float, y: Float): Boolean {
        return boundingBox.contains(x, y)
    }

    /**
     * 获取二维码的中心点
     */
    fun getCenter(): Pair<Float, Float> {
        return Pair(boundingBox.centerX(), boundingBox.centerY())
    }
}
