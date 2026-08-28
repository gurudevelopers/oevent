import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

ksp {
    arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

// Required for Room schema export configuration
room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    jvm()
    
    js {
        browser()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    android {
       namespace = "com.sendmystatus.oeventapp.app.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
       withDeviceTestBuilder {
           sourceSetTreeName = "test"
       }.configure {
           instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
    }
    
    sourceSets {
        val nonWebMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.sqlite.bundled)
            }
        }

        commonMain.apply {
            dependencies {
                api(project(":core"))
                implementation(libs.kotlin.inject.runtime)
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.materialIconsExtended)
                implementation(libs.compose.ui)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.androidx.navigation.compose)
                implementation(libs.kotlinx.serialization.json)
                
                implementation(libs.ktor.clientCore)
                implementation(libs.ktor.clientAuth)
                implementation(libs.ktor.clientContentNegotiation)
                implementation(libs.ktor.clientLogging)
                implementation(libs.ktor.serializationKotlinxJson)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization.json)
            }
        }
        androidMain.apply {
            get().dependsOn(nonWebMain)
            dependencies {
                implementation(libs.compose.uiToolingPreview)
                implementation(libs.compose.uiTooling)
                implementation(libs.qr.kit)
                implementation(libs.ktor.clientOkHttp)
            }
        }
        val iosMain = create("iosMain") {
            dependsOn(nonWebMain)
            dependencies {
                implementation(libs.qr.kit)
                implementation(libs.ktor.clientDarwin)
            }
        }
        getByName("iosArm64Main").dependsOn(iosMain)
        getByName("iosSimulatorArm64Main").dependsOn(iosMain)
        
        getByName("jvmMain").apply {
            dependsOn(nonWebMain)
            dependencies {
                implementation(libs.qr.kit)
                implementation(libs.ktor.clientJava)
            }
        }
        getByName("wasmJsMain").dependencies {
            implementation(libs.qr.kit)
            implementation(libs.ktor.clientJs)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutinesTest)
        }
        jsMain.dependencies {
            implementation(libs.wrappers.browser)
        }
    }
}

// Link KSP generated sources for commonMain
kotlin.sourceSets.commonMain {
    kotlin.srcDir(layout.buildDirectory.dir("generated/ksp/metadata/commonMain/kotlin"))
}

// Ensure all tasks that use common KSP output depend on it explicitly to avoid implicit dependency warnings.
val kspCommonMainTask = tasks.matching { it.name == "kspCommonMainKotlinMetadata" }
tasks.configureEach {
    if (name != "kspCommonMainKotlinMetadata" && (name.contains("Kotlin") || name.startsWith("ksp"))) {
        dependsOn(kspCommonMainTask)
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.kotlin.inject.compiler)
    androidRuntimeClasspath(libs.compose.uiTooling)
}
