# scanQR 项目概览

## 项目基本信息

| 项目名称 | scanQR |
|---------|--------|
| 包名 | com.example.scanqr |
| 项目类型 | 纯本地Android二维码扫描器 |
| 开发语言 | Kotlin |
| 构建工具 | Gradle (Kotlin DSL) |

---

## 目录结构

```
scanQR/
├── ARCHITECTURE.md              # 架构设计文档
├── build.gradle.kts             # 顶层构建配置
├── settings.gradle.kts          # Gradle项目设置
├── gradle.properties            # Gradle属性配置
├── local.properties             # 本地配置（SDK路径）
├── gradle/                      # Gradle wrapper目录
├── gradlew / gradlew.bat        # Gradle wrapper脚本
└── app/                         # 主应用模块
    ├── build.gradle.kts         # 应用层级构建配置
    └── src/main/
        ├── AndroidManifest.xml  # 应用清单文件
        ├── java/com/example/scanqr/
        │   ├── MainActivity.kt              # 主Activity
        │   ├── MainViewModel.kt             # ViewModel
        │   ├── scanner/                     # 扫码模块
        │   │   ├── CameraManager.kt         # 相机管理器
        │   │   ├── QrCodeAnalyzer.kt        # 二维码分析器
        │   │   └── QRCodeInfo.kt            # 二维码数据类
        │   ├── ui/                          # UI层
        │   │   ├── CameraScreen.kt          # 相机扫码界面
        │   │   ├── CameraPreview.kt         # 相机预览组件
        │   │   ├── ResultScreen.kt          # 结果展示页
        │   │   └── theme/
        │   │       ├── Color.kt             # 颜色定义
        │   │       └── Theme.kt             # 主题配置
        │   └── utils/
        │       └── ClipboardHelper.kt       # 剪贴板工具
        └── res/                              # 资源文件
            ├── values/                       # 字符串、颜色、主题
            ├── values-night/                 # 深色主题
            ├── drawable/                     # 图标资源
            ├── mipmap-*/                     # 应用图标
            └── xml/                          # 备份规则等
```

---

## 应用配置

### 版本信息
- 编译SDK: 36 (Android 16)
- 最小SDK: 30 (Android 11)
- 目标SDK: 36 (Android 16)
- 版本号: 1
- 版本名: 1.0

### 权限
- `CAMERA` - 相机权限

### 主要依赖库

| 用途 | 库 |
|------|----|
| 相机 | CameraX (camera-camera2, camera-lifecycle, camera-view) |
| 扫码 | ML Kit Barcode Scanning (本地模型) |
| UI | Jetpack Compose + Material3 |
| 权限 | accompanist-permissions |

---

## 核心功能

1. **相机扫码** - 实时扫描二维码
2. **多二维码支持** - 同时检测多个，点击选择
3. **结果展示** - 完整展示扫码内容
4. **一键复制** - 快速复制到剪贴板
5. **权限管理** - 运行时相机权限请求

---

## 技术栈

| 类别 | 技术 |
|------|------|
| 语言 | Kotlin |
| UI框架 | Jetpack Compose + Material3 |
| 相机 | CameraX |
| 扫码 | ML Kit Barcode Scanning (本地模型) |
| 架构 | MVVM (ViewModel + StateFlow) |

---

## 关键文件

| 文件 | 描述 |
|------|------|
| `MainActivity.kt` | 应用入口，权限处理和界面导航 |
| `CameraManager.kt` | 封装CameraX相机操作 |
| `QrCodeAnalyzer.kt` | ML Kit二维码分析器 |
| `CameraPreview.kt` | 相机预览+二维码边界框绘制 |
| `ResultScreen.kt` | 扫码结果展示页 |

---

## 隐私设计

- ✅ 完全本地处理，不联网
- ✅ ML Kit 使用本地模型
- ✅ 无第三方 SDK 数据收集
