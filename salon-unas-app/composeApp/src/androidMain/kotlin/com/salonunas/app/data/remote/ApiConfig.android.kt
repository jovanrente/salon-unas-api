package com.salonunas.app.data.remote

// On Android emulator, the host machine's localhost is reachable at 10.0.2.2.
// For a physical device, replace with the server's LAN IP (e.g. http://192.168.1.10:8080).
actual object PlatformApiBaseUrl {
    actual val value: String = "http://10.0.2.2:8080"
}
