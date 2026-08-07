# Política de seguridad

## Versiones soportadas

| Versión | Soporte |
|---------|---------|
| 1.0.x   | ✅ Activo |

## Reportar vulnerabilidades

Si descubres una vulnerabilidad de seguridad:

1. **No abras un issue público.**
2. Envía un correo a security@komizen.local con detalles.
3. Incluye pasos de reproducción y impacto estimado.
4. Espera 90 días antes de divulgación pública coordinada.

## Medidas implementadas

- `EncryptedSharedPreferences` para datos sensibles.
- ProGuard/R8 para ofuscación en release.
- NetworkSecurityConfig con cleartext deshabilitado.
- Validación de firmas APK antes de instalación.
- Secrets solo via `local.properties` (no commiteado).
