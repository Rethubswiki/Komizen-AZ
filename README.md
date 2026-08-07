# 🌀 Komizen-AZ

> **Hub Android para el ecosistema Komizen** — gestión de extensiones, OCR, tracking, sincronización y más.

![CI](https://github.com/Rethubswiki/Komizen-AZ/actions/workflows/ci.yml/badge.svg)

---

## 📖 Descripción general

**Komizen-AZ** es una aplicación Android todo‑en‑uno diseñada para el ecosistema **Komizen**: un punto central donde gestionar extensiones de lectura (manga, novelas), seguimiento de progreso, sincronización con servicios externos (AniList, MyAnimeList, Simkl), y herramientas avanzadas como OCR y traducción integrada.

**Basado en:** [Kototoro-Nightly](https://github.com/Kototoro-app/Kototoro-Nightly) con integraciones de [Manatan](https://github.com/KolbyML/Manatan) (OCR), [Tonkatsu Box](https://github.com/hacan359/tonkatsu_box) (tracking), [Tadami](https://miyomi.app/software/tadami) (UI), [Nuvio](https://miyomi.app/software/nuvio) (video), [AnymeX](https://miyomi.app/software/anymex) (multi‑plataforma) y [Anizen](https://miyomi.app/software/anizen) (IA).

---

## ✨ Características principales

**Extensiones**
- Instalación y actualización de extensiones desde repositorios Mihon/Tachiyomi
- Compatible con tres formatos de índice: `index.json` (legacy store), `array.json` (array plano moderno) y `.pb` (protobuf)
- Deduplicación por package name con resolución de versionCode más alto
- ClassLoader dinámico para carga de extensiones instaladas

**OCR + Traducción**
- Captura y traducción de texto en imágenes (subs, manga) con ML Kit on‑device
- Sin dependencias de servicios en la nube para preservar privacidad

**Seguimiento de progreso**
- Listas personales, estado de lectura/visionado, colecciones visuales
- Integración con AniList, MAL y Simkl vía OAuth 2.0
- Cola offline con sincronización diferida cuando hay conectividad

**Reproductor de vídeo**
- Soporte avanzado de subtítulos (SSA/ASS, SRT)
- Anime4K para escalado en tiempo real
- DLNA/Chromecast para streaming local

**Recomendaciones**
- Motor basado en TF‑IDF + MMR (Maximal Marginal Relevance)
- Manejo de cold‑start con pesos configurables por género y popularidad

**UI/UX**
- Interfaz "Liquid Glass" con blur real en Android 12+ y fallback translúcido en versiones anteriores
- Modo oscuro, tema dinámico Material You (Monet) y paleta personalizable
- Navegación bottom‑bar con cuatro destinos principales

**CI/CD y calidad**
- Compilación automática con GitHub Actions (debug y release firmado)
- Lint con ktlint, análisis estático con CodeQL, tests unitarios e instrumentados
- Baseline Profile para reducir jank en arranque

---

## 🏗️ Arquitectura

Komizen-AZ sigue una arquitectura por capas con separación de responsabilidades:

**Capa de presentación (UI)**
- Jetpack Compose 100% declarativo
- ViewModel por pantalla con StateFlow unidireccional
- Koin para inyección de dependencias

**Capa de dominio / datos**
- Repository pattern con fuentes locales (Room) y remotas (Retrofit)
- DataStore para preferencias tipadas
- WorkManager para tareas en segundo plano (sincronización periódica)

**Capa de sistema**
- DownloadManager para descarga de APKs de extensiones
- FileProvider para compartir archivos de forma segura
- BroadcastReceiver para re‑programar workers tras reinicio

---

## 🛠️ Stack tecnológico

- **Lenguaje:** Kotlin 1.9.24 (JVM target 17)
- **UI:** Jetpack Compose BOM 2024.06.00, Material3, Navigation Compose
- **DI:** Koin 3.5.6
- **Base de datos:** Room 2.6.1 + KSP
- **Red:** Retrofit 2.11.0 + OkHttp 4.12.0 + Gson
- **Imágenes:** Coil 2.7.0
- **Async:** Kotlin Coroutines + Flow
- **Seguridad:** AndroidX Security Crypto (EncryptedSharedPreferences)
- **Build:** Gradle 8.7 con Android Gradle Plugin 8.4.0
- **Calidad de código:** ktlint, lintDebug, CodeQL

---

## 🚀 Compilación local

### Requisitos previos

- JDK 17 (Temurin recomendado)
- Android Studio Hedgehog (2023.1.1) o superior
- Android SDK API 34 con build‑tools 34.0.0

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/Rethubswiki/Komizen-AZ.git
cd Komizen-AZ

# 2. Dar permisos de ejecución al wrapper
chmod +x gradlew

# 3. Compilar APK debug
./gradlew assembleDebug

# 4. El APK resultante se encuentra en:
# app/build/outputs/apk/debug/app-debug.apk
```

### Compilación release (local)

```bash
# Requiere un keystore en app/release.keystore
./gradlew assembleRelease
```

---

## 🔌 Repositorio de extensiones por defecto

Komizen-AZ viene preconfigurado con el repositorio agregado:

```
https://rethubswiki.github.io/Multi-Extension-Komizen/index.min.json
```

Este índice contiene **429 extensiones deduplicadas** provenientes de 12 fuentes distintas (Mihon, Aniyomi, novelas, etc.). El parser interno detecta automáticamente el formato del índice (array plano vs legacy store) sin intervención del usuario.

---

## 📦 Release firmado (producción)

Para generar un APK release firmado vía GitHub Actions, configura los siguientes secrets en tu repositorio:

- `SIGNING_KEY` — Keystore codificado en Base64 (`base64 -w0 release.keystore`)
- `ALIAS` — Alias del keystore
- `KEY_STORE_PASSWORD` — Contraseña del keystore
- `KEY_PASSWORD` — Contraseña de la clave privada

Una vez configurados, crea y empuja un tag:

```bash
git tag -a v1.0.3 -m "Release v1.0.3"
git push origin v1.0.3
```

El workflow `release.yml` generará automáticamente el APK firmado y lo publicará en la sección Releases del repositorio.

---

## 🧪 Testing y calidad

```bash
# Tests unitarios
./gradlew testDebugUnitTest

# Lint de Kotlin
./gradlew ktlintCheck

# Lint de Android
./gradlew lintDebug

# Generar Baseline Profile (requiere emulador/dispositivo físico)
./gradlew :app:generateBaselineProfile
```

---

## 🗺️ Roadmap

**Corto plazo (v1.1.x)**
- Soporte para instalación de extensiones vía ClassLoader dinámico con aislamiento de permisos
- Sincronización bidireccional con AniList (progreso de capítulos)
- Búsqueda difusa (fuzzy) en el catálogo de extensiones

**Medio plazo (v1.2.x)**
- Motor OCR con soporte para textos verticales (japonés tradicional)
- Reproductor integrado con soporte para archivos locales y streaming HLS
- Sistema de plugins JAR con verificación de firma

**Largo plazo (v2.x)**
- Backend auto‑alojado para sincronización entre dispositivos
- Integración con LNReader para novelas ligeras
- Soporte para Android TV (Leanback)

---

## 🤝 Contribuir

1. Haz un fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nombre‑feature`)
3. Realiza tus cambios y asegúrate de que pasen los checks de CI
4. Haz commit (`git commit -m "feat: descripción clara"`)
5. Abre una Pull Request hacia `main`

Antes de contribuir, revisa las plantillas de issues y PRs en `.github/`.

---

## 🛡️ Seguridad

- Las contraseñas y tokens OAuth se almacenan con EncryptedSharedPreferences
- El tráfico de red fuerza TLS 1.2+ (`android:usesCleartextTraffic="false"`)
- Los APKs descargados se validan por checksum antes de la instalación
- CodeQL analiza automáticamente cada PR en busca de vulnerabilidades

---

## 📄 Licencia

MIT — consulta el archivo `LICENSE` para más detalles.

---

## 🙌 Créditos y agradecimientos

- **Kototoro** — base del proyecto y arquitectura de extensiones
- **Manatan** — motor OCR y pipelines de visión por computadora
- **Tonkatsu Box** — sistema de tracking y metadatos
- **Tadami** — referencias de UI Aurora y animaciones
- **Nuvio** — reproductor de vídeo y decodificación
- **AnymeX** — sincronización multi‑plataforma
- **Anizen** — integraciones de IA y recomendaciones

---

## 📬 Contacto y comunidad

- Issues: [github.com/Rethubswiki/Komizen-AZ/issues](https://github.com/Rethubswiki/Komizen-AZ/issues)
- Discusiones: activa GitHub Discussions en tu fork

---

¡Disfruta de Komizen-AZ! 🚀
