#!/bin/bash
set -e

echo "=== Komizen-AZ Dev Setup ==="

# Check Java
if ! command -v java &> /dev/null; then
    echo "ERROR: Java no encontrado. Instala JDK 17."
    exit 1
fi

JAVA_VER=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
echo "Java: $JAVA_VER"

# Make gradlew executable
chmod +x gradlew

# Download dependencies
echo "Descargando dependencias..."
./gradlew dependencies --configuration compileClasspath

echo "Setup completo. Ejecuta ./gradlew assembleDebug para compilar."
