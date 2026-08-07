# Solucion Completa — Komizen-AZ CI/CD Fixes v2.1

**Fecha:** 2026-08-07
**Repo:** github.com/Rethubswiki/Komizen-AZ
**Problema:** Todos los workflows CI fallan; 26 PRs de Dependabot abiertos con incompatibilidades.

---

## Diagnostico resumido

1. **Causa raiz:** `gradle-wrapper.jar` no existe en el repo. Sin el, `./gradlew` falla en todos los workflows.
2. **Cascada:** Dependabot detecto versiones nuevas pero no considero compatibilidad cruzada (AGP 8.4 + Gradle 8.7 + Kotlin 1.9.24 + Compose Compiler 1.5.14).
3. **Archivo corrupto:** Existe `gradle-wrapper.propertie...` truncado en `gradle/wrapper/`.

---

## Paso 1: Subir gradle-wrapper.jar (CRITICO)

### Opcion A — GitHub Web (recomendada desde movil)

1. Ve a `github.com/Rethubswiki/Komizen-AZ/tree/main/gradle/wrapper`
2. Pulsa **Add file** → **Upload files**
3. Descarga el JAR desde este enlace directo:
   ```
   https://raw.githubusercontent.com/gradle/gradle/v8.7.0/gradle/wrapper/gradle-wrapper.jar
   ```
4. Sube el archivo al path exacto: `gradle/wrapper/gradle-wrapper.jar`
5. Commit: `chore: add gradle-wrapper.jar`

### Opcion B — Termux

```bash
cd ~/storage/shared/Android/Komizen-AZ  # ajusta tu ruta
bash scripts/download-wrapper.sh
git add gradle/wrapper/gradle-wrapper.jar
git commit -m "chore: add gradle-wrapper.jar"
git push origin main
```

### Opcion C — Fallback sin JAR (ya aplicado en los workflows de este ZIP)

Los workflows corregidos en este ZIP incluyen fallback a `gradle` del sistema si falta el wrapper jar. Esto permite que el CI funcione mientras subes el JAR.

---

## Paso 2: Eliminar archivo truncado

1. Ve a `github.com/Rethubswiki/Komizen-AZ/tree/main/gradle/wrapper`
2. Busca `gradle-wrapper.propertie...` (el nombre truncado)
3. Abrelo → pulsa 🗑️ **Delete this file**
4. Commit: `chore: remove truncated gradle-wrapper duplicate`

---

## Paso 3: Aplicar archivos corregidos de este ZIP

Sube cada archivo a su path exacto en el repo:

| Archivo en ZIP | Path en repo | Accion |
|---|---|---|
| `.github/dependabot.yml` | `.github/dependabot.yml` | Reemplazar completo |
| `.github/workflows/ci.yml` | `.github/workflows/ci.yml` | Reemplazar completo |
| `.github/workflows/benchmark.yml` | `.github/workflows/benchmark.yml` | Reemplazar completo |
| `.github/workflows/release.yml` | `.github/workflows/release.yml` | Reemplazar completo |
| `.github/workflows/codeql.yml` | `.github/workflows/codeql.yml` | Reemplazar completo |
| `gradle/wrapper/gradle-wrapper.properties` | `gradle/wrapper/gradle-wrapper.properties` | Reemplazar completo |

---

## Paso 4: Limpiar PRs de Dependabot

### Cerrar estos PRs (rechazar):
#17, #5, #4, #6, #22, #10, #20, #21, #12, #8, #24, #15, #16, #9, #7, #14, #11

### Aceptar estos PRs (seguros):
#25, #19, #18 (junto con #3), #26, #13, #2

**Nota sobre #18 y #3:** lifecycle-viewmodel-compose y lifecycle-runtime-ktx deben actualizarse juntas. Si Dependabot los propuso por separado, acepta ambos o ninguno.

---

## Paso 5: Verificar CI

1. Ve a **Actions** en GitHub
2. El workflow **CI** deberia ejecutarse automaticamente al hacer push de los cambios
3. Si falla, revisa el log. Los workflows corregidos tienen mensajes de advertencia claros.

---

## Paso 6: Probar release (opcional)

Configura los secrets en GitHub → Settings → Secrets and variables → Actions:
- `SIGNING_KEY`
- `ALIAS`
- `KEY_STORE_PASSWORD`
- `KEY_PASSWORD`

Luego crea un tag:
```bash
git tag -a v1.0.3 -m "Release v1.0.3"
git push origin v1.0.3
```

---

## Troubleshooting post-fix

| Sintoma | Causa probable | Solucion |
|---|---|---|
| `gradlew not found` | No se subio el script gradlew | Verificar que `gradlew` este en raiz |
| `Could not find gradle-wrapper.jar` | Falta el JAR binario | Aplicar Paso 1 |
| `Plugin [id: 'com.android.application'] was not found` | `settings.gradle.kts` sin `pluginManagement` | Verificar que settings.gradle.kts tenga bloque pluginManagement al inicio |
| ktlint falla | Dependabot actualizo ktlint a 14.x | Revertir a 12.1.0 en `build.gradle.kts` raiz |
| Room compilation error | Dependabot actualizo Room sin KSP | Revertir Room a 2.6.1 o actualizar KSP tambien |
| Compose compiler error | Kotlin actualizado sin Compose Compiler compatible | Kotlin debe ser 1.9.24 con Compose Compiler 1.5.14 |

---

## Matriz de compatibilidad verificada

| Componente | Version fija | Nota |
|---|---|---|
| Android Gradle Plugin | 8.4.0 | No subir a 9.x |
| Gradle | 8.7 | Maximo soportado por AGP 8.4 |
| Kotlin | 1.9.24 | Compose Compiler 1.5.14 lo requiere |
| KSP | 1.9.24-1.0.20 | Ligado a version de Kotlin |
| Compose Compiler | 1.5.14 | Maximo para Kotlin 1.9.24 |
| Compose BOM | 2024.06.00 | Estable |

---

**Fin de instrucciones.**
