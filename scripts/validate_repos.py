#!/usr/bin/env python3
"""Valida la sintaxis y estructura de archivos index.json de repositorios."""
import json
import sys
from pathlib import Path
from urllib.parse import urlparse

def validate_repo(path: Path) -> bool:
    valid = True
    try:
        with open(path, "r", encoding="utf-8") as f:
            data = json.load(f)
    except json.JSONDecodeError as e:
        print(f"[ERROR] {path}: JSON inválido — {e}")
        return False

    entries = data if isinstance(data, list) else data.get("sources", [])
    if not entries:
        print(f"[WARN] {path}: Sin entradas")
        return True

    seen_pkgs = set()
    for i, entry in enumerate(entries):
        pkg = entry.get("pkg")
        if not pkg:
            print(f"[ERROR] {path}[{i}]: falta 'pkg'")
            valid = False
            continue
        if pkg in seen_pkgs:
            print(f"[WARN] {path}[{i}]: duplicado '{pkg}'")
        seen_pkgs.add(pkg)

        apk = entry.get("apk", "")
        if not apk.startswith(("http://", "https://")):
            print(f"[ERROR] {path}[{i}]: URL APK inválida '{apk}'")
            valid = False

    print(f"[OK] {path}: {len(entries)} entradas, {len(seen_pkgs)} únicas")
    return valid

if __name__ == "__main__":
    files = sys.argv[1:] or ["index.json"]
    all_valid = all(validate_repo(Path(f)) for f in files)
    sys.exit(0 if all_valid else 1)
