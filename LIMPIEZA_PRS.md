# Limpieza de PRs de Dependabot — Komizen-AZ

## PRs a RECHAZAR/CERRAR (breaking changes)

| PR | Libreria | De → A | Razon |
|---|---|---|---|
| #17 | org.jetbrains.kotlin.android | 1.9.24 → 2.4.10 | Rompe Compose Compiler 1.5.14 y KSP |
| #5 | com.android.application | 8.4.0 → 9.3.1 | Requiere Gradle 9.x + JDK 21 |
| #4 | gradle-wrapper | 8.7 → 9.6.1 | Incompatible con AGP 8.4.0 |
| #6 | retrofit2:retrofit | 2.11.0 → 3.0.0 | Retrofit 3.x no estable |
| #22 | okhttp3:okhttp | 4.12.0 → 5.4.0 | OkHttp 5.x alpha; rompe Retrofit 2.x |
| #10 | okhttp3:logging-interceptor | 4.12.0 → 5.4.0 | Misma razon que OkHttp |
| #20 | koin-android | 3.5.6 → 4.2.2 | Breaking changes en modulos Compose |
| #21 | koin-androidx-compose | 3.5.6 → 4.2.2 | Breaking changes |
| #12 | room:room-ktx | 2.6.1 → 2.8.4 | Requiere KSP mas nuevo |
| #8 | room:room-compiler | 2.6.1 → 2.8.4 | Cascada de updates |
| #24 | ktlint | 12.1.0 → 14.2.0 | Breaking rules, reformato masivo |
| #15 | actions/checkout | 4 → 7 | checkout@v7 no existe |
| #16 | gradle/actions/setup-gradle | 3.3.2 → 6.3.0 | v6.3.0 inestable/no existe |
| #9 | actions/upload-artifact | 4 → 7 | upload-artifact@v7 no existe |
| #7 | actions/setup-java | 4 → 5 | setup-java@v5 no existe |
| #14 | codeql-action | 3 → 4 | codeql-action@v4 no existe |
| #11 | softprops/action-gh-release | 2 → 3 | Requiere verificacion previa |

## PRs a ACEPTAR (seguros)

| PR | Libreria | De → A | Nota |
|---|---|---|---|
| #25 | security-crypto | 1.1.0-alpha06 → 1.1.0 | Estable, mismo API |
| #19 | gson | 2.11.0 → 2.14.0 | Compatible |
| #18 | lifecycle-viewmodel-compose | 2.8.3 → 2.11.0 | Aceptar JUNTO con #3 |
| #3 | lifecycle-runtime-ktx | 2.8.3 → 2.11.0 | Aceptar JUNTO con #18 |
| #26 | mockk | 1.13.11 → 1.14.11 | Solo test, seguro |
| #13 | test-ext:junit | 1.2.1 → 1.3.0 | Solo test, seguro |
| #2 | espresso-core | 3.6.1 → 3.7.0 | Solo test, seguro |
| #1 | kotlinx-coroutines-test | 1.8.1 → 1.11.0 | Solo test; verificar que kotlinx-coroutines-android tambien se actualice si aceptas |

## Como cerrar un PR desde GitHub mobile/web

1. Abre el PR
2. Desplazate hasta el final
3. Pulsa **Close pull request**
4. (Opcional) Comenta: `@dependabot ignore this dependency`
