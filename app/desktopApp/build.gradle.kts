import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":app:shared"))

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)
}

compose.desktop {
    application {
        mainClass = "com.sendmystatus.oeventapp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.sendmystatus.oeventapp"
            packageVersion = "1.0.0"
            macOS {
                bundleID = "com.sendmystatus.oeventapp"
                infoPlist {
                    extraKeysRawXml = """
                        <key>NSCameraUsageDescription</key>
                        <string>This app requires access to the camera to scan QR codes.</string>
                    """.trimIndent()
                }
                // If using App Sandbox / Hardened Runtime, include camera entitlement
                entitlementsFile.set(project.file("entitlements.plist"))
            }
        }
    }
}