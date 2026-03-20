package com.example.scanqr

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.scanqr.ui.theme.ScanQRTheme
import com.example.scanqr.ui.CameraScreen
import com.example.scanqr.ui.ResultScreen

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        _permissionGranted.value = isGranted
    }

    private val _permissionGranted = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 检查初始权限状态
        _permissionGranted.value = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        setContent {
            ScanQRTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        viewModel = viewModel,
                        permissionGranted = _permissionGranted.value,
                        requestPermission = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    permissionGranted: Boolean,
    requestPermission: () -> Unit
) {
    val context = LocalContext.current
    var scanResult by remember { mutableStateOf<String?>(null) }

    when {
        permissionGranted -> {
            when {
                scanResult != null -> {
                    ResultScreen(
                        result = scanResult!!,
                        onCopy = {
                            viewModel.copyToClipboard(context, scanResult!!)
                        },
                        onScanAgain = {
                            scanResult = null
                        }
                    )
                }
                else -> {
                    CameraScreen(
                        onScanResult = { result ->
                            scanResult = result
                        }
                    )
                }
            }
        }
        else -> {
            PermissionRationaleScreen(onRequestPermission = requestPermission)
        }
    }
}

@Composable
fun PermissionRationaleScreen(onRequestPermission: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "需要相机权限",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "本应用需要相机权限才能扫描二维码",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onRequestPermission) {
                Text("授予权限")
            }
        }
    }
}
