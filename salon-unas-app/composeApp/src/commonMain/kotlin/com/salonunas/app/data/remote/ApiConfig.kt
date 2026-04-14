package com.salonunas.app.data.remote

/**
 * Base URL of the salon-unas-api Spring Boot backend.
 *
 * Notes:
 *  - Android emulator must use 10.0.2.2 to reach the host machine's localhost.
 *  - iOS simulator can use localhost directly, but a real device needs the LAN IP.
 *
 * The platform-specific override is resolved in [PlatformApiBaseUrl].
 */
expect object PlatformApiBaseUrl {
    val value: String
}

object ApiConfig {
    val baseUrl: String get() = PlatformApiBaseUrl.value
}
