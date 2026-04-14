# salon-unas-app

App mobile para **Android** e **iOS** escrita en Kotlin con **Compose Multiplatform**.
Consume la API Spring Boot del proyecto `salon-unas-api` (repo aparte).

## Estructura

```
salon-unas-app/
├── composeApp/
│   └── src/
│       ├── commonMain/   # Código y UI compartidos (Compose, Ktor, ViewModels)
│       ├── androidMain/  # Entrypoint Android (MainActivity, Manifest)
│       └── iosMain/      # Entrypoint iOS (MainViewController)
├── iosApp/               # Proyecto SwiftUI que embebe el binario de Compose
├── gradle/libs.versions.toml
├── settings.gradle.kts
└── build.gradle.kts
```

## Requisitos

| Plataforma | Requisitos |
|------------|------------|
| Android    | Android Studio Ladybug+ o JDK 17 + Android SDK |
| iOS        | macOS con Xcode 15+ |
| Ambos      | Kotlin 2.0.21 (lo maneja Gradle automáticamente) |

## 1. Arrancar la API (repo `salon-unas-api`)

Desde el repo del backend:

```bash
./gradlew bootRun
```

La API queda disponible en `http://localhost:8080`. CORS ya está habilitado
para `/api/**` (ver `WebConfig.kt` en el repo backend).

## 2. Configurar el host al que llama la app

- **Android emulator:** usa `10.0.2.2:8080` (ya configurado en
  `composeApp/src/androidMain/kotlin/com/salonunas/app/data/remote/ApiConfig.android.kt`).
- **iOS simulator:** usa `localhost:8080` (ya configurado en
  `composeApp/src/iosMain/kotlin/com/salonunas/app/data/remote/ApiConfig.ios.kt`).
- **Dispositivo físico (Android o iOS):** cambia el valor de `PlatformApiBaseUrl`
  al IP LAN de tu máquina, por ejemplo `http://192.168.1.10:8080`.

## 3. Ejecutar en Android

### Opción A — Android Studio
1. Abre la carpeta `salon-unas-app/` en Android Studio.
2. Espera a que Gradle sincronice.
3. Selecciona la configuración **composeApp** y dale *Run*.

### Opción B — línea de comandos
```bash
./gradlew :composeApp:installDebug
```

> La primera vez Gradle descarga el Android SDK si no lo tienes. Necesitas
> definir `ANDROID_HOME` o crear `local.properties` con
> `sdk.dir=/ruta/al/Android/Sdk`.

## 4. Ejecutar en iOS

1. Abre un proyecto Xcode en `iosApp/` (usa el *KMP Project Wizard* de
   JetBrains en https://kmp.jetbrains.com/ para generar el `.xcodeproj`
   conectado al framework de Compose).
2. En Xcode, agrega un *Run Script Phase* al target `iosApp` con:
   ```bash
   cd "$SRCROOT/.."
   ./gradlew :composeApp:embedAndSignAppleFrameworkForXcode
   ```
3. Selecciona un simulador y pulsa *Run*.

## 5. Funcionalidades actuales

La app tiene tres pestañas (clientes, servicios, manicuristas). Para cada una:

- Listado (GET `/api/{recurso}`)
- Crear (POST `/api/{recurso}`)
- Eliminar (DELETE `/api/{recurso}/{id}`)

## Siguientes pasos sugeridos

- [ ] Pantalla de detalle y edición
- [ ] Inyección de dependencias (Koin)
- [ ] Navegación con `androidx.navigation` multiplatform
- [ ] Persistencia local con SQLDelight o Room-KMP
- [ ] Autenticación
