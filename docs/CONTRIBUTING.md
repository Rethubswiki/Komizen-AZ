# Contribuir a Komizen-AZ

## Requisitos

- Android Studio Hedgehog o superior
- JDK 17
- Git

## Flujo de trabajo

1. Fork del repositorio.
2. Crea una rama: `git checkout -b feature/nombre`.
3. Commits con mensajes descriptivos.
4. Abre un PR usando la plantilla proporcionada.

## Estilo de código

- Kotlin oficial style guide.
- Ejecuta `./gradlew ktlintCheck` antes de commitear.
- Máximo 120 caracteres por línea.

## Tests

- Unit tests con JUnit + MockK.
- UI tests con Compose Test + Espresso.
- Benchmarks con Macrobenchmark.

## Reportar bugs

Usa la plantilla de GitHub Issues y proporciona:
- Versión de la app
- Dispositivo y Android
- Pasos de reproducción
- Logs si es posible
