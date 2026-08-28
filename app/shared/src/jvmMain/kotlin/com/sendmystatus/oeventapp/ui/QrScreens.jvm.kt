package com.sendmystatus.oeventapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.opencv.core.Mat
import org.opencv.objdetect.QRCodeDetector
import org.opencv.videoio.VideoCapture
import qrgenerator.qrkitpainter.rememberQrKitPainter
import java.awt.FileDialog
import java.awt.Frame
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.File
import javax.imageio.ImageIO

private object OpenCVLoader {
    private var isLoaded = false
    fun load(): Boolean {
        if (!isLoaded) {
            try {
                nu.pattern.OpenCV.loadLocally()
                isLoaded = true
            } catch (e: Throwable) {
                e.printStackTrace()
                isLoaded = false
            }
        }
        return isLoaded
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun ScannerScreen(
    onScan: (String) -> Unit,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var cameraIndex by remember { mutableStateOf(0) }
    var previewBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var cameraError by remember { mutableStateOf<String?>(null) }
    var isCameraActive by remember { mutableStateOf(true) }
    var isCameraInitializing by remember { mutableStateOf(true) }
    var showManualInputDialog by remember { mutableStateOf(false) }
    var manualCodeInput by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var hasScanned by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    fun processDecodedQr(qrText: String) {
        val trimmed = qrText.trim()
        if (trimmed.isNotEmpty() && !hasScanned) {
            hasScanned = true
            isCameraActive = false
            onScan(trimmed)
        }
    }

    fun pickAndScanImage() {
        coroutineScope.launch(Dispatchers.IO) {
            val bufferedImage = pickQrImageFile()
            if (bufferedImage != null) {
                val decoded = decodeQrCode(bufferedImage)
                if (decoded != null) {
                    withContext(Dispatchers.Main) {
                        processDecodedQr(decoded)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        snackbarHostState.showSnackbar("No QR code found in selected image.")
                    }
                }
            }
        }
    }

    fun pasteAndScan() {
        coroutineScope.launch(Dispatchers.IO) {
            val (text, image) = readQrFromClipboard()
            if (text != null && text.isNotBlank()) {
                withContext(Dispatchers.Main) {
                    processDecodedQr(text)
                }
            } else if (image != null) {
                val decoded = decodeQrCode(image)
                if (decoded != null) {
                    withContext(Dispatchers.Main) {
                        processDecodedQr(decoded)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        snackbarHostState.showSnackbar("No QR code found in clipboard image.")
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    snackbarHostState.showSnackbar("Clipboard does not contain image or text.")
                }
            }
        }
    }

    // Camera capture loop
    LaunchedEffect(cameraIndex, isCameraActive) {
        if (!isCameraActive || hasScanned) return@LaunchedEffect

        isCameraInitializing = true
        cameraError = null

        withContext(Dispatchers.IO) {
            val loaded = OpenCVLoader.load()
            if (!loaded) {
                withContext(Dispatchers.Main) {
                    cameraError = "OpenCV native library failed to load on this system."
                    isCameraInitializing = false
                }
                return@withContext
            }

            var videoCapture: VideoCapture? = null
            try {
                videoCapture = VideoCapture(cameraIndex)
                if (!videoCapture.isOpened) {
                    withContext(Dispatchers.Main) {
                        cameraError = "Webcam could not be opened (camera index: $cameraIndex). Please check camera permissions in macOS System Settings > Privacy & Security > Camera."
                        isCameraInitializing = false
                    }
                    return@withContext
                }

                withContext(Dispatchers.Main) {
                    isCameraInitializing = false
                    cameraError = null
                }

                val mat = Mat()
                val qrDetector = QRCodeDetector()
                var frameCounter = 0

                while (isActive && isCameraActive && !hasScanned) {
                    if (videoCapture.read(mat) && !mat.empty()) {
                        val buffered = matToBufferedImage(mat)
                        val composeBitmap = buffered.toComposeImageBitmap()

                        withContext(Dispatchers.Main) {
                            previewBitmap = composeBitmap
                        }

                        // Try decoding every frame or every 2 frames
                        frameCounter++
                        if (frameCounter % 2 == 0) {
                            // First try ZXing
                            var qrText = decodeQrCode(buffered)
                            // Fallback to OpenCV QRCodeDetector
                            if (qrText.isNullOrBlank()) {
                                val cvQr = qrDetector.detectAndDecode(mat)
                                if (!cvQr.isNullOrBlank()) {
                                    qrText = cvQr
                                }
                            }

                            if (!qrText.isNullOrBlank()) {
                                withContext(Dispatchers.Main) {
                                    processDecodedQr(qrText)
                                }
                                break
                            }
                        }
                    }
                    // ~30 FPS throttle
                    kotlinx.coroutines.delay(33)
                }
                mat.release()
            } catch (e: Throwable) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    cameraError = "Camera error: ${e.localizedMessage ?: "Unknown error"}"
                    isCameraInitializing = false
                }
            } finally {
                try {
                    videoCapture?.release()
                } catch (ignored: Throwable) {}
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            isCameraActive = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.scan_qr_code)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(Res.string.back))
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            cameraIndex = if (cameraIndex == 0) 1 else 0
                        }
                    ) {
                        Icon(Icons.Default.FlipCameraIos, contentDescription = "Switch Camera")
                    }
                    IconButton(
                        onClick = { pickAndScanImage() }
                    ) {
                        Icon(Icons.Default.Image, contentDescription = "Upload Image")
                    }
                    IconButton(
                        onClick = { pasteAndScan() }
                    ) {
                        Icon(Icons.Default.ContentPaste, contentDescription = "Paste")
                    }
                    IconButton(
                        onClick = { showManualInputDialog = true }
                    ) {
                        Icon(Icons.Default.Keyboard, contentDescription = "Manual Input")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            val currentPreview = previewBitmap
            if (currentPreview != null && cameraError == null) {
                Image(
                    bitmap = currentPreview,
                    contentDescription = "Camera Feed",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Viewfinder Target Box Overlay
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                )

                Text(
                    text = stringResource(Res.string.align_qr_code),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 40.dp)
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            } else if (isCameraInitializing && cameraError == null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Text(
                        text = "Starting camera...",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            if (cameraError != null) {
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp)
                        .fillMaxWidth(0.85f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            Icons.Default.VideocamOff,
                            contentDescription = "Camera Unavailable",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "Webcam Unavailable",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = cameraError ?: "Unable to access camera",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )

                        Text(
                            text = "You can still scan a QR code by choosing an option below:",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    cameraError = null
                                    cameraIndex = if (cameraIndex == 0) 1 else 0
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Retry / Switch")
                            }
                            Button(
                                onClick = { pickAndScanImage() },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Pick File")
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { pasteAndScan() },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Paste")
                            }
                            OutlinedButton(
                                onClick = { showManualInputDialog = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Enter Code")
                            }
                        }
                    }
                }
            }

            if (showManualInputDialog) {
                AlertDialog(
                    onDismissRequest = { showManualInputDialog = false },
                    title = { Text("Enter QR Code Data") },
                    text = {
                        Column {
                            Text("Paste or type the QR code / event token:")
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = manualCodeInput,
                                onValueChange = { manualCodeInput = it },
                                label = { Text("QR Code / Token") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (manualCodeInput.isNotBlank()) {
                                    val code = manualCodeInput.trim()
                                    showManualInputDialog = false
                                    processDecodedQr(code)
                                }
                            }
                        ) {
                            Text("Submit")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showManualInputDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

private fun matToBufferedImage(mat: Mat): BufferedImage {
    val width = mat.cols()
    val height = mat.rows()
    val channels = mat.channels()
    val sourcePixels = ByteArray(width * height * channels)
    mat.get(0, 0, sourcePixels)

    val imageType = if (channels == 1) BufferedImage.TYPE_BYTE_GRAY else BufferedImage.TYPE_3BYTE_BGR
    val image = BufferedImage(width, height, imageType)
    val targetPixels = (image.raster.dataBuffer as DataBufferByte).data
    System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.size)
    return image
}

private fun decodeQrCode(bufferedImage: BufferedImage): String? {
    return try {
        val source = BufferedImageLuminanceSource(bufferedImage)
        val bitmap = BinaryBitmap(HybridBinarizer(source))
        val hints = mapOf<DecodeHintType, Any>(
            DecodeHintType.TRY_HARDER to true
        )
        val result = MultiFormatReader().decode(bitmap, hints)
        result.text
    } catch (e: Exception) {
        null
    }
}

private fun pickQrImageFile(): BufferedImage? {
    return try {
        val fileDialog = FileDialog(null as Frame?, "Select QR Code Image", FileDialog.LOAD)
        fileDialog.setFilenameFilter { _, name ->
            val lower = name.lowercase()
            lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") ||
                lower.endsWith(".bmp") || lower.endsWith(".gif") || lower.endsWith(".webp")
        }
        fileDialog.isVisible = true
        val directory = fileDialog.directory
        val file = fileDialog.file
        if (directory != null && file != null) {
            val selectedFile = File(directory, file)
            if (selectedFile.exists()) {
                ImageIO.read(selectedFile)
            } else null
        } else null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun readQrFromClipboard(): Pair<String?, BufferedImage?> {
    try {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val contents = clipboard.getContents(null) ?: return Pair(null, null)
        if (contents.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            val img = contents.getTransferData(DataFlavor.imageFlavor) as? java.awt.Image
            if (img != null) {
                val buffered = BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB)
                val g = buffered.createGraphics()
                g.drawImage(img, 0, 0, null)
                g.dispose()
                return Pair(null, buffered)
            }
        }
        if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            val text = contents.getTransferData(DataFlavor.stringFlavor) as? String
            return Pair(text?.trim(), null)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return Pair(null, null)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun RewardScreen(
    rewardData: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.your_reward)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(Res.string.back))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.show_qr_to_redeem),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            val qrPainter = rememberQrKitPainter(data = rewardData)

            Image(
                painter = qrPainter,
                contentDescription = stringResource(Res.string.reward_qr_code),
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(Res.string.reward_id, rewardData),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
