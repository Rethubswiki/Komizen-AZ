# Arquitectura de Komizen-AZ

## Visión general

Komizen-AZ sigue una arquitectura MVVM con capas claras:

```
UI Layer (Compose + ViewModels)
    ↓
Domain Layer (Use Cases — opcional)
    ↓
Data Layer (Repositories)
    ↓
Local / Remote (Room / Retrofit)
```

## Capas

### UI Layer
- **Jetpack Compose** para todas las pantallas.
- **Navigation Compose** para navegación entre pestañas.
- **ViewModels** con `StateFlow` para estado reactivo.

### Data Layer
- **Repository Pattern**: `ExtensionRepository` coordina fuentes locales y remotas.
- **Room**: Caché local de metadatos de extensiones.
- **Retrofit + OkHttp**: Consumo de índices JSON remotos.

### Inyección de dependencias
- **Koin** para DI ligero sin generación de código en tiempo de compilación.

## Flujo de datos

1. Usuario abre "Explorar".
2. `BrowseViewModel` solicita extensiones a `ExtensionRepository`.
3. Repository intenta red primero; si falla, usa caché local.
4. Resultado expuesto como `StateFlow` a la UI.

## Seguridad

- `EncryptedSharedPreferences` para datos sensibles.
- Sin secrets hardcodeados; todos via `BuildConfigField` o `local.properties`.
- ProGuard/R8 activo en release.
