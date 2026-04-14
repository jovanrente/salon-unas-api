package com.salonunas.app.data.remote

// On iOS simulator, localhost resolves to the host machine.
// For a physical device, change to the server's LAN IP (e.g. http://192.168.1.10:8080).
actual object PlatformApiBaseUrl {
    actual val value: String = "http://localhost:8080"
}
