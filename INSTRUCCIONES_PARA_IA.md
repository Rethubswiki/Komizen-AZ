# 📋 Instrucciones para IA asistente — Subida de Komizen-AZ desde Acode

**Destinatario:** Otra instancia de IA asistente del usuario.
**Contexto:** El usuario necesita subir el proyecto Komizen-AZ a GitHub usando la app Acode en Android.
**ZIP de referencia:** `komizen-az-v2.zip` (contiene el repo completo y compilable).

---

## 🎯 Objetivo

Subir el contenido del ZIP al repositorio `github.com/Rethubswiki/Komizen-AZ` (rama `main`) usando únicamente la app Acode (editor de código Android).

---

## ⚠️ Advertencias previas

1. **No borres el contenido previo del repo sin confirmar con el usuario.** Si el repo ya tiene archivos (`.github/workflows/ci.yml`, `gradle/wrapper`, etc.), pregunta si desea sobrescribir o fusionar.
2. **El archivo `gradle-wrapper.jar` es binario.** Acode no puede generarlo desde texto. Si falta en el repo remoto, el usuario debe subirlo vía GitHub web o usar `git add --force gradle/wrapper/gradle-wrapper.jar` desde Termux.
3. **Los secrets de firma (`SIGNING_KEY`, etc.) no deben incluirse en el repo.** Se configuran únicamente en GitHub → Settings → Secrets and variables → Actions.

---

## 📁 Estructura del ZIP a verificar

Al descomprimir `komizen-az-v2.zip`, la estructura debe ser:

```
Komizen-AZ/
├── .editorconfig
├── .gitignore
├── .nojekyll
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── README.md
├── settings.gradle.kts
├── .github/
│   ├── dependabot.yml
│   ├── pull_request_template.md
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md
│   │   └── feature_request.md
│   └── workflows/
│       ├── benchmark.yml
│       ├── ci.yml
│       ├── codeql.yml
│       └── release.yml
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── androidTest/...
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/komizen/az/...
│       │   └── res/...
│       └── test/...
├── baselineprofile/
│   ├── build.gradle.kts
│   └── src/main/java/...
└── gradle/
    └── wrapper/
        └── gradle-wrapper.properties
```

**Archivos críticos que Acode NO puede crear como binarios:**
- `gradle/wrapper/gradle-wrapper.jar` (binario, ~60KB)

**Solución si falta:** El usuario puede subirlo manualmente desde GitHub web en el path correcto, o generarlo localmente con `gradle wrapper --gradle-version 8.7` si tiene Termux + Gradle instalado.

---

## 🔧 Pasos de subida con Acode

### Paso 1 — Preparar el entorno en Acode

1. Abre Acode y crea un nuevo proyecto o abre la carpeta raíz donde descomprimiste el ZIP.
2. Asegúrate de que la estructura de carpetas coincida exactamente con la del repo remoto.

### Paso 2 — Crear/editar archivos uno por uno

Acode permite crear archivos y carpetas. Para cada archivo del ZIP:

1. Navega al directorio destino (o créalo con el botón "+").
2. Crea el archivo con el nombre exacto (incluyendo extensiones `.kt`, `.yml`, `.xml`).
3. Copia y pega el contenido del archivo correspondiente del ZIP.
4. Guarda (icono de disco o gesto de guardar).

**Orden recomendado de creación (de arriba hacia abajo):**

1. Archivos raíz: `README.md`, `.gitignore`, `.editorconfig`, `.nojekyll`, `settings.gradle.kts`, `build.gradle.kts`, `gradle.properties`
2. Scripts Gradle: `gradlew`, `gradlew.bat` (marcar como ejecutable si Acode lo permite, o hacerlo luego vía GitHub web/Termux)
3. Carpeta `.github/` completa
4. Carpeta `gradle/wrapper/gradle-wrapper.properties`
5. Carpeta `app/` completa (build.gradle.kts primero, luego sources)
6. Carpeta `baselineprofile/` completa

### Paso 3 — Subir a GitHub

Acode tiene integración con Git. Si el usuario tiene configurada su cuenta:

1. Inicializa Git en la carpeta raíz si no está inicializado:
   ```bash
   git init
   git remote add origin https://github.com/Rethubswiki/Komizen-AZ.git
   ```
2. Añade todos los archivos:
   ```bash
   git add .
   ```
3. Si hay archivos que Git ignora por defecto (como `.github/`), verifica que `.gitignore` no los excluya.
4. Commit:
   ```bash
   git commit -m "feat: Komizen-AZ v1.0.2 — repo completo y compilable"
   ```
5. Push:
   ```bash
   git push -u origin main
   ```

**Si Acode no tiene acceso a Git o el usuario prefiere la web:**
- Ve a `github.com/Rethubswiki/Komizen-AZ` en el navegador
- Usa el botón "Add file" → "Create new file" o "Upload files" para cada archivo
- Para archivos de texto (`.kt`, `.xml`, `.yml`, `.md`), usa "Create new file" y pega el contenido
- Para carpetas, escribe el path completo en el campo de nombre (ej: `app/src/main/java/com/komizen/az/MainActivity.kt`)

### Paso 4 — Verificación post-subida

Después de subir, verifica en GitHub web que existan:

- [ ] `README.md` renderizado correctamente
- [ ] `.github/workflows/ci.yml` en su lugar
- [ ] `settings.gradle.kts` con `pluginManagement` al inicio
- [ ] `app/build.gradle.kts` con las dependencias de Compose, Room, Koin, etc.
- [ ] `app/src/main/AndroidManifest.xml` con permisos de INTERNET y REQUEST_INSTALL_PACKAGES
- [ ] `app/src/main/java/com/komizen/az/ui/theme/` con Theme.kt, Color.kt, Type.kt
- [ ] `app/src/main/java/com/komizen/az/worker/SyncWorker.kt`
- [ ] `baselineprofile/` con su `build.gradle.kts`

### Paso 5 — Disparar CI

Una vez subido todo a `main`, el workflow `ci.yml` debería ejecutarse automáticamente. El usuario puede verificarlo en la pestaña **Actions** de GitHub.

---

## 🆘 Troubleshooting para la IA asistente

**Problema:** El usuario dice que GitHub Actions falla con "gradlew not found".
**Solución:** Verificar que `gradlew` esté en la raíz y sea ejecutable. Si se subió vía web, el bit de ejecución se pierde. El workflow hace `chmod +x gradlew`, así que el archivo debe existir físicamente.

**Problema:** Error "Could not find gradle-wrapper.jar".
**Solución:** El JAR binario no está en el repo. Opciones:
1. Subir manualmente `gradle-wrapper.jar` vía GitHub web (upload file)
2. O usar Termux para ejecutar `gradle wrapper` y luego hacer push

**Problema:** Ktlint falla en CI.
**Solución:** Revisar que todos los archivos `.kt` terminen en nueva línea y usen 4 espacios de indentación. El `.editorconfig` ya lo especifica.

**Problema:** SettingsScreen.kt no compila.
**Solución:** Verificar que exista `SettingsViewModel.kt` en el mismo package y que `strings.xml` contenga todas las claves referenciadas.

---

## 📦 Notas sobre el ZIP generado

- **Total de archivos:** ~65 archivos de texto + recursos XML + placeholders de drawable
- **Binarios omitidos intencionalmente:** `gradle-wrapper.jar` (debe generarse o subirse por vías alternas)
- **Compatibilidad:** Android Gradle Plugin 8.4.0, Gradle 8.7, Kotlin 1.9.24, Compose BOM 2024.06.00
- **API mínima:** 26 (Android 8.0)
- **API objetivo:** 34 (Android 14)

---

**Fin de instrucciones.**
