# ScanQR 应用架构清单

## 项目概述
一个纯本地的 Android 二维码扫描器，专为快速获取电脑上的文本信息设计。
- 包名: `com.example.scanqr`
- 语言: Kotlin
- 最小 SDK: 30 (Android 11)
- 目标 SDK: 36 (Android 16)

---

## 核心功能需求
1. **扫码入口** - 进入应用后点击按钮启动扫码
2. **摄像头扫描** - 调用摄像头实时扫描二维码
3. **结果展示** - 扫描成功后展示文本内容
4. **快速复制** - 底部悬浮按钮一键复制文本
5. **大二维码优化** - 优化大型二维码的识别速度

---

## 技术选型

### 扫码库
| 选项 | 优点 | 缺点 | 选择 |
|------|------|------|------|
| **ML Kit** | Google 官方、识别快、支持本地模型 | 依赖 Google Play | ✅ 推荐 |
| ZXing | 完全开源、无依赖 | 识别速度较慢 | 备选 |

### 摄像头
- **CameraX** - Google 推荐的现代相机库，简化相机操作

### UI 框架
- **Jetpack Compose** - 现代、简洁、快速开发

---

## 项目结构

```
app/src/main/java/com/example/scanqr/
├── MainActivity.kt                 # 主入口
├── ui/
│   ├── MainActivity.kt           # Compose 主界面
│   ├── CameraPreview.kt           # 相机预览组件
│   ├── ResultScreen.kt            # 扫码结果展示页
│   └── theme/
│       ├── Color.kt
│       └── Theme.kt
├── scanner/
│   ├── QrCodeAnalyzer.kt          # ML Kit 图像分析器
│   └── CameraManager.kt           # 相机管理器
└── utils/
    └── ClipboardHelper.kt         # 剪贴板工具类
```

---

## 依赖库清单

```kotlin
dependencies {
    // 基础库 (已有)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // CameraX
    implementation("androidx.camera:camera-camera2:1.4.1")
    implementation("androidx.camera:camera-lifecycle:1.4.1")
    implementation("androidx.camera:camera-view:1.4.1")

    // ML Kit 条码扫描 (本地模型)
    implementation("com.google.mlkit:barcode-scanning:17.3.0")

    // Jetpack Compose (可选，如需现代化 UI)
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
}
```

---

## 权限需求

```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.CAMERA" />

<!-- Android 13+ -->
<uses-feature android:name="android.hardware.camera.any" android:required="false" />
```

---

## 功能实现步骤

### Phase 1: 基础设置
- [x] 添加 CameraX 依赖
- [x] 添加 ML Kit 依赖
- [x] 配置相机权限
- [x] 实现运行时权限请求

### Phase 2: 相机与扫码
- [x] 创建 CameraPreview 组件
- [x] 集成 CameraX
- [x] 实现 QrCodeAnalyzer (ML Kit)
- [x] 处理扫码回调

### Phase 3: UI 界面
- [x] 主界面布局
- [x] 扫码按钮
- [x] 结果展示页
- [x] 底部悬浮复制按钮

### Phase 4: 复制功能
- [x] 实现剪贴板工具类
- [x] 添加复制成功提示 (Toast/Snackbar)

### Phase 5: 性能优化 (针对大二维码)
- [ ] 调整 ML Kit 识别模式 (BALANCED vs FAST)
- [ ] 优化相机分辨率配置
- [ ] 添加图像预处理 (可选)

---

## 关键技术点

### 1. ML Kit 扫码优化
```kotlin
val options = BarcodeScannerOptions.Builder()
    .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
    .build()
```

### 2. CameraX 配置
```kotlin
val cameraProvider = ProcessCameraProvider.getInstance(context)
val preview = Preview.Builder().build()
val imageAnalysis = ImageAnalysis.Builder()
    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
    .build()
```

### 3. 权限处理 (Compose)
```kotlin
val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
```

---

## 隐私设计

- ✅ 完全本地处理，不联网
- ✅ ML Kit 本地模型
- ✅ 无第三方 SDK 数据收集
- ✅ 相机数据仅用于扫码

---

## 测试清单
- [ ] 相机启动正常 (需要在真机测试)
- [ ] 扫码识别准确
- [ ] 大二维码识别速度
- [ ] 复制功能正常
- [ ] 横竖屏切换正常
- [ ] 后台恢复正常

---

## 版本规划
- **v1.0**: 基础扫码功能
- **v1.1**: 历史记录功能
- **v1.2**: 生成二维码功能
