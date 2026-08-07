#!/bin/bash
# Verifica que no haya secrets hardcodeados en el código
set -e

PATTERNS=(
    "AKIA[0-9A-Z]{16}"
    "ghp_[a-zA-Z0-9]{36}"
    "sk-[a-zA-Z0-9]{48}"
    "-----BEGIN (RSA |DSA |EC |OPENSSH )?PRIVATE KEY-----"
    "password\s*=\s*"[^"]+""
    "api_key\s*=\s*"[^"]+""
)

FOUND=0
for pattern in "${PATTERNS[@]}"; do
    if grep -rE "$pattern" --include="*.kt" --include="*.kts" --include="*.xml" --include="*.properties" app/ 2>/dev/null; then
        echo "[FAIL] Patrón sospechoso encontrado: $pattern"
        FOUND=1
    fi
done

if [ $FOUND -eq 0 ]; then
    echo "[OK] No se detectaron secrets hardcodeados."
else
    exit 1
fi
