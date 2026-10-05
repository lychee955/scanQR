# scanQR

English | [简体中文](./README.zh-CN.md)

A simple, fully local, privacy-first QR code scanner for Android.

> **What you scan is what you get: view the raw content of a QR code, with no automatic redirects or actions.**

**Scan. Read. Nothing else.**

<p align="center">
  <img src="https://img.shields.io/badge/Min%20SDK-API%2030%20(Android%2011)-blue.svg" alt="Min SDK">
  <img src="https://img.shields.io/badge/Target%20SDK-API%2036%20(Android%2016)-green.svg" alt="Target SDK">
  <img src="https://img.shields.io/badge/Language-Kotlin-orange.svg" alt="Language">
  <img src="https://img.shields.io/badge/Architecture-MVVM-purple.svg" alt="Architecture">
</p>

## Why scanQR?

Many QR code scanners automatically open websites, launch apps, or perform other actions based on a QR code's content.

scanQR follows a simple principle:

**Scanning reads the content. You decide what happens next.**

Whether a QR code contains a URL, a deep link, or plain text, scanQR displays only its **raw content**.

See exactly what you scanned before deciding what to do next.

## Features

- 👁️ **What you scan is what you get**: view the raw content of a QR code directly
- 🚫 **No automatic redirects**: URLs, deep links, and other content are displayed only as text
- 🛡️ **No automatic actions**: no automatic opening of websites or apps, or triggering of other actions
- 🔒 **Fully local processing**: no internet connection required, and scanned content is never uploaded
- 📷 Scan QR codes in real time using the camera
- 🔍 Detect multiple QR codes at once
- 📋 Select a QR code from the list at the bottom to view its content
- 📝 Copy raw content to the clipboard with one tap
- 🎨 Clean Material Design 3 interface

## Privacy and Security

scanQR minimizes permissions, network access, and automatic behavior.

- ✅ QR code recognition runs entirely on-device
- ✅ Scanned content is never uploaded to a server
- ✅ URLs, deep links, and other content are displayed only as raw text
- ✅ No automatic opening of websites
- ✅ No automatic launching of third-party apps
- ✅ No automatic actions based on QR code content
- ✅ ML Kit uses an on-device model
- ✅ No data collection by third-party SDKs
- ✅ Camera data is used only for QR code recognition
- ✅ Requests only the `CAMERA` permission

**The QR code determines what you see. You determine what happens next.**

## Tech Stack

| Category | Technology |
| --- | --- |
| Language | Kotlin |
| UI Framework | Jetpack Compose + Material 3 |
| Camera | CameraX 1.4.1 |
| QR Code Scanning | ML Kit Barcode Scanning 17.3.0 |
| Architecture | MVVM (ViewModel + StateFlow) |
| Permission Handling | Accompanist Permissions 0.36.0 |
| Build | Gradle (Kotlin DSL) |

## Project Structure

```text
app/
├── src/main/java/com/example/scanqr/
│   ├── MainActivity.kt           # Main Activity: permissions + navigation
│   ├── MainViewModel.kt          # ViewModel: clipboard state management
│   ├── scanner/                  # Scanning module
│   │   ├── CameraManager.kt      # CameraX lifecycle management
│   │   ├── QrCodeAnalyzer.kt     # ML Kit analyzer (500 ms throttling)
│   │   └── QRCodeInfo.kt         # QR code data class
│   ├── ui/                       # UI layer (Jetpack Compose)
│   │   ├── CameraScreen.kt       # Scanning screen + QR code list
│   │   ├── CameraPreview.kt      # Camera preview component
│   │   ├── ResultScreen.kt       # Raw content display + copy action
│   │   └── theme/                # Material 3 theme
│   └── utils/
│       └── ClipboardHelper.kt    # Clipboard utility
```

## Getting Started

### Build

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Install on a connected device
./gradlew installDebug
```

### Install the APK

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Usage

1. Grant camera permission.
2. Place a QR code in the viewfinder.
3. Once a QR code is detected, the results appear at the bottom of the screen.
4. If multiple QR codes are detected, select one from the list.
5. Tap a QR code to view its **raw text content**.
6. If needed, tap “Copy” to copy the content to the clipboard.
7. Tap “Continue Scanning” to return to the scanning screen.

> [!IMPORTANT]
> scanQR never automatically opens websites, launches apps, or performs other actions based on QR code content.

For example, when you scan:

```text
https://example.com
```

scanQR simply displays:

```text
https://example.com
```

**It does not automatically open a browser.**

## Requirements

- Minimum Android version: Android 11 (API 30)
- Target Android version: Android 16 (API 36)
- Java version: 11

## Recent Updates

### v1.0

- Improved the UI layout
- Adjusted bottom spacing and button positions
- ResultScreen: placed the Copy and Continue Scanning buttons side by side
- CameraScreen: added bottom padding to the list to prevent content from being obscured
- Fixed release build signing issues

## Design Philosophy

scanQR does not try to be an all-purpose QR code scanner.

It does just three things:

**Scan. Read. Display.**

No network access, no automatic redirects, and no automatic next steps on your behalf.

That is why scanQR exists.

## License

Licensed under the [Apache License 2.0](LICENSE).

---

If you find scanQR useful, consider giving it a star ⭐
