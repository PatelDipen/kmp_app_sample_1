package com.kmp.core.network

actual object ApiConfig {
    // Android emulator needs 10.0.2.2 to reach host machine localhost.
    actual val baseUrl: String = "http://10.0.2.2:3000"
}

