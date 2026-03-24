# scanQR

一个简洁、高效的纯本地 Android 二维码扫描器

<p align="center">
  <img src="https://img.shields.io/badge/Min%20SDK-API%2030%20(Android%2011)-blue.svg" alt="Min SDK">
  <img src="https://img.shields.io/badge/Target%20SDK-API%2036%20(Android%2016)-green.svg" alt="Target SDK">
  <img src="https://img.shields.io/badge/Language-Kotlin-orange.svg" alt="Language">
  <img src="https://img.shields.io/badge/Architecture-MVVM-purple.svg" alt="Architecture">
</p>

## 功能特性

- 📷 相机实时扫描二维码
- 🔍 支持同时检测多个二维码
- 📋 底部列表选择，点击即可查看内容
- 📝 一键复制到剪贴板
- 🔒 完全本地处理，**不联网**，保护隐私
- 🎨 Material Design 3 简洁界面
- ✨ 优化的 UI 布局：底部按钮并排显示，操作更便捷

## 技术栈

| 类别 | 技术 |
|------|------|
| 语言 | Kotlin |
| UI 框架 | Jetpack Compose + Material3 |
| 相机 | CameraX 1.4.1 |
| 扫码 | ML Kit Barcode Scanning 17.3.0 (本地模型) |
| 架构 | MVVM (ViewModel + StateFlow) |
| 权限处理 | Accompanist Permissions 0.36.0 |
| 构建 | Gradle (Kotlin DSL) |

## 隐私设计

- ✅ 完全本地处理，无网络请求
- ✅ ML Kit 使用本地模型
- ✅ 无第三方 SDK 数据收集
- ✅ 相机数据仅用于扫码
- ✅ 仅申请 CAMERA 权限

## 项目结构

```
app/
├── src/main/java/com/example/scanqr/
│   ├── MainActivity.kt           # 主 Activity：权限处理 + 导航
│   ├── MainViewModel.kt          # ViewModel：剪贴板状态管理
│   ├── scanner/                  # 扫码模块
│   │   ├── CameraManager.kt      # CameraX 生命周期管理
│   │   ├── QrCodeAnalyzer.kt     # ML Kit 分析器（500ms 节流）
│   │   └── QRCodeInfo.kt         # 二维码数据类
│   ├── ui/                       # UI 层（Jetpack Compose）
│   │   ├── CameraScreen.kt       # 扫码界面 + 二维码列表
│   │   ├── CameraPreview.kt      # 相机预览组件
│   │   ├── ResultScreen.kt       # 结果展示 + 复制功能
│   │   └── theme/                # Material3 主题
│   └── utils/
│       └── ClipboardHelper.kt    # 剪贴板工具
```

## 快速开始

### 构建

```bash
# Debug 构建
./gradlew assembleDebug

# Release 构建
./gradlew assembleRelease

# 安装到连接的设备
./gradlew installDebug
```

### 安装

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 使用说明

1. 授予相机权限
2. 将二维码放入取景框内
3. 检测到二维码后，底部会显示列表
4. 点击想要查看的二维码
5. 点击「复制」按钮复制内容，或「继续扫码」返回

## 配置要求

- 最低 Android 版本：Android 11 (API 30)
- 目标 Android 版本：Android 16 (API 36)
- Java 版本：11

## 最近更新

### v1.0 (最新)
- 优化 UI 布局：调整底部间距和按钮位置
- ResultScreen：复制按钮与继续扫码按钮并排显示
- CameraScreen：增加列表底部 padding，避免内容被遮挡
- 修复 Release 包签名问题

## 许可证

本项目仅供学习交流使用。

---

如果对你有帮助，请给个 Star ⭐️
