#!/bin/bash
# Quick compile + run for Linux/macOS
cd "$(dirname "$0")"
mkdir -p out
echo "Compiling..."
javac -d out $(find src -name "*.java")
if [ $? -ne 0 ]; then
  echo "Compile failed. Make sure JDK 17+ is installed."
  exit 1
fi
echo "Running..."
java -cp out com.hospital.Main
