# Komizen-AZ v1.0.2

Hub Android para gestión de extensiones del ecosistema Komizen.

## Estructura

- `app/` — Módulo principal (Jetpack Compose)
- `.github/workflows/` — CI/CD con setup-gradle@v3
- `scripts/` — Utilidades de generación y validación
- `docs/` — Documentación técnica

## Compilación

```bash
./gradlew assembleRelease
```

## Fixes v1.0.2

- `setup-gradle@v3` sin `./gradlew` explícito
- `pluginManagement` en `settings.gradle.kts`
- Benchmark PR condicional
- Baseline profile validación explícita
- Secrets defensivos (no hardcodeados)

## Licencia

MIT
