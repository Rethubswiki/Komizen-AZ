#!/bin/bash
# Script para descargar gradle-wrapper.jar oficial de Gradle 8.7
# Uso: bash scripts/download-wrapper.sh

set -e

WRAPPER_DIR="gradle/wrapper"
JAR_URL="https://raw.githubusercontent.com/gradle/gradle/v8.7.0/gradle/wrapper/gradle-wrapper.jar"

echo "[INFO] Creando directorio $WRAPPER_DIR si no existe..."
mkdir -p "$WRAPPER_DIR"

echo "[INFO] Descargando gradle-wrapper.jar desde GitHub oficial..."
if command -v curl &> /dev/null; then
    curl -L -o "$WRAPPER_DIR/gradle-wrapper.jar" "$JAR_URL"
elif command -v wget &> /dev/null; then
    wget -O "$WRAPPER_DIR/gradle-wrapper.jar" "$JAR_URL"
else
    echo "[ERROR] Ni curl ni wget estan disponibles. Instala uno de ellos."
    exit 1
fi

echo "[INFO] Verificando descarga..."
if [ -f "$WRAPPER_DIR/gradle-wrapper.jar" ]; then
    SIZE=$(stat -c%s "$WRAPPER_DIR/gradle-wrapper.jar" 2>/dev/null || stat -f%z "$WRAPPER_DIR/gradle-wrapper.jar")
    echo "[OK] gradle-wrapper.jar descargado: $SIZE bytes"
else
    echo "[ERROR] La descarga fallo."
    exit 1
fi
