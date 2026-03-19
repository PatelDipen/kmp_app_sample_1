import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)

            // Android-specific Coroutines
            implementation(libs.kotlinx.coroutines.android)

            // Android-specific Koin
            implementation(libs.koin.android)

            // Ktor - Android engine
            implementation(libs.ktor.client.okhttp)

            // SQLDelight - Android driver
            implementation(libs.sqldelight.android.driver)
        }

        commonMain.dependencies {
            // --- Compose UI ---
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)

            // --- Lifecycle ---
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // --- Navigation ---
            implementation(libs.navigation.compose)

            // --- Dependency Injection (Koin) ---
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // --- Networking (Ktor) ---
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)

            // --- Serialization ---
            implementation(libs.kotlinx.serialization.json)

            // --- Local Database (SQLDelight) ---
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)

            // --- Preferences ---
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)

            // --- Image Loading (Coil3) ---
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            // --- Coroutines ---
            implementation(libs.kotlinx.coroutines.core)

            // --- Date/Time ---
            implementation(libs.kotlinx.datetime)

            // --- Logging ---
            implementation(libs.napier)
        }

        iosMain.dependencies {
            // Ktor - iOS engine
            implementation(libs.ktor.client.darwin)

            // SQLDelight - iOS (native) driver
            implementation(libs.sqldelight.native.driver)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
            implementation(libs.koin.test)
        }

        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.mockk)
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.kmp.database")
        }
    }
}

android {
    namespace = "com.kmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.kmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}
