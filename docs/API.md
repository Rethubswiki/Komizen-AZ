# API y Formatos de Repositorio

## Formatos soportados

### Array plano (moderno)
Raíz es un array de objetos:

```json
[
  {
    "name": "Extension Name",
    "pkg": "eu.kanade.tachiyomi.extension.en.example",
    "apk": "https://host.com/extension.apk",
    "lang": "en",
    "version": "1.2.3",
    "versionCode": 123,
    "nsfw": 0,
    "icon": "https://host.com/icon.png",
    "sources": [
      {
        "id": "123456789",
        "name": "Source Name",
        "lang": "en",
        "baseUrl": "https://example.com"
      }
    ]
  }
]
```

### Legacy Store
Raíz es un objeto con campo `sources`:

```json
{
  "name": "Repo Name",
  "sources": [ ... ]
}
```

## Endpoints

La app consume índices vía GET HTTP. No hay autenticación requerida para repositorios públicos.

## Headers

- `User-Agent: Komizen-AZ/1.0.2`
- `Accept: application/json`
