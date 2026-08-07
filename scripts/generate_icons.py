#!/usr/bin/env python3
"""Genera íconos adaptativos en múltiples resoluciones desde un SVG fuente."""
import argparse
import os
import subprocess
import sys

DENSITIES = {
    "mdpi": 48,
    "hdpi": 72,
    "xhdpi": 96,
    "xxhdpi": 144,
    "xxxhdpi": 192,
}

def generate(svg_path: str, output_dir: str):
    os.makedirs(output_dir, exist_ok=True)
    for density, size in DENSITIES.items():
        dir_path = os.path.join(output_dir, f"mipmap-{density}")
        os.makedirs(dir_path, exist_ok=True)
        png_path = os.path.join(dir_path, "ic_launcher.png")
        cmd = [
            "inkscape",
            svg_path,
            "--export-type=png",
            f"--export-filename={png_path}",
            f"--export-width={size}",
            f"--export-height={size}",
        ]
        print(f"Generating {density} ({size}x{size})...")
        subprocess.run(cmd, check=True)
    print("Done.")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Generate launcher icons")
    parser.add_argument("svg", help="Source SVG file")
    parser.add_argument("-o", "--output", default="app/src/main/res", help="Output res directory")
    args = parser.parse_args()
    generate(args.svg, args.output)
