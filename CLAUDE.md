# CLAUDE.md

本文件为 Claude Code (claude.ai/code) 在此仓库中工作时提供指导。

## 项目概述

scanQR 是一个使用现代 Android 技术构建的纯本地二维码扫描器，专注于隐私保护——所有处理都在设备上完成，无需网络连接。

- **包名**: `com.example.scanqr`
- **语言**: Kotlin
- **架构**: MVVM (Model-View-ViewModel)
- **构建工具**: Gradle with Kotlin DSL

## 构建命令

```bash
# Debug 构建
./gradlew assembleDebug

# Release 构建
./gradlew assembleRelease

# 清理构建
./gradlew clean build

# 安装到连接的设备
./gradlew installDebug
```

## 测试命令

```bash
# 运行单元测试
./gradlew test

# 运行仪器化测试
./gradlew connectedAndroidTest
```

## 代码架构

### 核心组件

- **MainActivity.kt**: 入口点，处理相机权限和屏幕之间的导航
- **MainViewModel.kt**: 使用 StateFlow 管理剪贴板复制状态的 ViewModel
- **CameraManager.kt**: CameraX 生命周期包装器，配置和管理相机
- **QrCodeAnalyzer.kt**: ML Kit 条形码扫描器，500ms 节流检测二维码
- **CameraScreen.kt**: Jetpack Compose 扫描 UI，包含二维码列表
- **ResultScreen.kt**: 显示扫描内容并提供复制功能

### 技术栈

| 类别 | 库 |
|------|-----|
| 相机 | CameraX 1.4.1 |
| 扫描 | ML Kit Barcode Scanning 17.3.0 |
| UI | Jetpack Compose (BOM 2024.12.01) + Material3 |
| 架构 | ViewModel + StateFlow |
| 权限 | Accompanist Permissions 0.36.0 |

### 构建配置

- **Compile SDK**: 36 (Android 16)
- **Min SDK**: 30 (Android 11)
- **Target SDK**: 36 (Android 16)
- **Java 版本**: 11
- **Gradle**: 9.3.1

### 权限

仅需要 `CAMERA` 权限——按设计不请求网络权限。
