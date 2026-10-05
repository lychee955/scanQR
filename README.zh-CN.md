# scanQR

[English](./README.md) | 简体中文

一个简洁、纯本地、隐私优先的 Android 二维码扫描器。

> **所扫即所得：只展示二维码中的原始内容，不自动跳转，不自动执行。**

**Scan. Read. Nothing else.**

<p align="center">
  <img src="https://img.shields.io/badge/Min%20SDK-API%2030%20(Android%2011)-blue.svg" alt="Min SDK">
  <img src="https://img.shields.io/badge/Target%20SDK-API%2036%20(Android%2016)-green.svg" alt="Target SDK">
  <img src="https://img.shields.io/badge/Language-Kotlin-orange.svg" alt="Language">
  <img src="https://img.shields.io/badge/Architecture-MVVM-purple.svg" alt="Architecture">
</p>

## 为什么是 scanQR？

很多扫码工具会根据二维码内容自动打开网页、唤起 App 或执行其他操作。

scanQR 的设计原则很简单：

**扫描只负责读取，不替用户做决定。**

无论二维码中包含 URL、Deep Link 还是普通文本，scanQR 都只展示它的**原始内容**。

你可以先看清楚扫到了什么，再决定下一步做什么。

## 功能特性

- 👁️ **所扫即所得**：直接展示二维码中的原始内容
- 🚫 **不自动跳转**：URL、Deep Link 等内容均只作为文本展示
- 🛡️ **不自动执行**：不会主动打开网页、应用或触发其他操作
- 🔒 **完全本地处理**：无需联网，扫码内容不会上传
- 📷 相机实时扫描二维码
- 🔍 支持同时检测多个二维码
- 📋 多个二维码可从底部列表中选择查看
- 📝 一键复制原始内容到剪贴板
- 🎨 Material Design 3 简洁界面

## 隐私与安全

scanQR 尽可能减少权限、联网和自动行为。

- ✅ 二维码识别完全在本地完成
- ✅ 扫描内容不会上传到服务器
- ✅ URL、Deep Link 等内容仅作为原始文本展示
- ✅ 不自动打开网页
- ✅ 不自动唤起第三方应用
- ✅ 不根据二维码内容自动执行操作
- ✅ ML Kit 使用本地模型
- ✅ 无第三方 SDK 数据收集
- ✅ 相机数据仅用于二维码识别
- ✅ 仅申请 `CAMERA` 权限

**你看到什么，由二维码决定；你接下来做什么，由你决定。**

## 技术栈

| 类别 | 技术 |
| --- | --- |
| 语言 | Kotlin |
| UI 框架 | Jetpack Compose + Material 3 |
| 相机 | CameraX 1.4.1 |
| 扫码 | ML Kit Barcode Scanning 17.3.0 |
| 架构 | MVVM（ViewModel + StateFlow） |
| 权限处理 | Accompanist Permissions 0.36.0 |
| 构建 | Gradle（Kotlin DSL） |

## 项目结构

```text
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
│   │   ├── ResultScreen.kt       # 原始内容展示 + 复制功能
│   │   └── theme/                # Material 3 主题
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

### 安装 APK

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 使用说明

1. 授予相机权限
2. 将二维码放入取景框
3. 检测到二维码后，底部会显示识别结果
4. 如果同时检测到多个二维码，可以从列表中选择
5. 点击二维码后查看其中的**原始文本内容**
6. 如有需要，点击「复制」将内容复制到剪贴板
7. 点击「继续扫码」返回扫描界面

> [!IMPORTANT]
> scanQR 不会根据二维码内容自动打开网页、启动应用或执行其他操作。

例如扫描：

```text
https://example.com
```

scanQR 展示的只是：

```text
https://example.com
```

**不会自动打开浏览器。**

## 配置要求

- 最低 Android 版本：Android 11（API 30）
- 目标 Android 版本：Android 16（API 36）
- Java 版本：11

## 最近更新

### v1.0

- 优化 UI 布局
- 调整底部间距和按钮位置
- ResultScreen：复制按钮与继续扫码按钮并排显示
- CameraScreen：增加列表底部 padding，避免内容被遮挡
- 修复 Release 包签名问题

## 设计理念

scanQR 不尝试成为一个“什么都能做”的扫码工具。

它只做三件事：

**扫描。读取。展示。**

不联网，不自动跳转，不替用户执行下一步操作。

这也是 scanQR 存在的理由。

## License

Licensed under the [Apache License 2.0](LICENSE).

---

如果 scanQR 对你有帮助，欢迎给个 Star ⭐
