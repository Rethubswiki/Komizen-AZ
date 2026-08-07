#!/usr/bin/env python3
"""Incrementa versionCode y versionName en build.gradle.kts."""
import re
import sys
from pathlib import Path

BUILD_FILE = Path("app/build.gradle.kts")

def bump():
    content = BUILD_FILE.read_text(encoding="utf-8")

    # Bump versionCode
    code_match = re.search(r'versionCode\s*=\s*(\d+)', content)
    if not code_match:
        print("versionCode no encontrado")
        sys.exit(1)
    old_code = int(code_match.group(1))
    new_code = old_code + 1
    content = content.replace(f'versionCode = {old_code}', f'versionCode = {new_code}')

    # Bump patch versionName
    name_match = re.search(r'versionName\s*=\s*"(\d+)\.(\d+)\.(\d+)"', content)
    if name_match:
        major, minor, patch = map(int, name_match.groups())
        new_name = f'{major}.{minor}.{patch + 1}'
        content = content.replace(f'versionName = "{major}.{minor}.{patch}"', f'versionName = "{new_name}"')
        print(f"versionCode: {old_code} → {new_code}")
        print(f"versionName: {major}.{minor}.{patch} → {new_name}")
    else:
        print(f"versionCode: {old_code} → {new_code}")

    BUILD_FILE.write_text(content, encoding="utf-8")
    print("Actualizado.")

if __name__ == "__main__":
    bump()
