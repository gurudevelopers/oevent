package com.sendmystatus.oeventapp

import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeWriter
import nu.pattern.OpenCV
import org.opencv.core.Mat
import org.opencv.objdetect.QRCodeDetector
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class QrScannerDesktopTest {

    @Test
    fun testOpenCVLoadsLocally() {
        OpenCV.loadLocally()
        val mat = Mat.eye(3, 3, org.opencv.core.CvType.CV_8UC1)
        assertNotNull(mat)
        assertEquals(3, mat.rows())
        assertEquals(3, mat.cols())
        mat.release()
    }

    @Test
    fun testZXingGeneratesAndDecodesQRCode() {
        val testData = "https://sendmystatus.com/event/12345"
        val bitMatrix = QRCodeWriter().encode(testData, BarcodeFormat.QR_CODE, 300, 300)
        val bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix)
        assertNotNull(bufferedImage)

        // Decode using ZXing
        val source = BufferedImageLuminanceSource(bufferedImage)
        val bitmap = BinaryBitmap(HybridBinarizer(source))
        val result = MultiFormatReader().decode(bitmap)
        assertEquals(testData, result.text)
    }

    @Test
    fun testOpenCVDetectorDecodesQRCode() {
        OpenCV.loadLocally()
        val testData = "TICKET_EVENT_ABC_999"
        val bitMatrix = QRCodeWriter().encode(testData, BarcodeFormat.QR_CODE, 300, 300)
        val bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix)

        // Convert BufferedImage to OpenCV Mat
        val mat = BufferedImageToMat(bufferedImage)
        val detector = QRCodeDetector()
        val decoded = detector.detectAndDecode(mat)
        assertEquals(testData, decoded)
        mat.release()
    }

    private fun BufferedImageToMat(image: BufferedImage): Mat {
        val converted = BufferedImage(image.width, image.height, BufferedImage.TYPE_3BYTE_BGR)
        val g = converted.createGraphics()
        g.drawImage(image, 0, 0, null)
        g.dispose()

        val pixels = (converted.raster.dataBuffer as DataBufferByte).data
        val mat = Mat(converted.height, converted.width, org.opencv.core.CvType.CV_8UC3)
        mat.put(0, 0, pixels)
        return mat
    }
}
