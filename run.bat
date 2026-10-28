@echo off
REM Quick compile + run for Windows
cd /d "%~dp0"
if not exist out mkdir out
echo Compiling...
javac -d out -sourcepath src src\com\hospital\Main.java src\com\hospital\model\*.java src\com\hospital\service\*.java src\com\hospital\ui\*.java
if errorlevel 1 (
  echo Compile failed. Make sure JDK 17+ is installed and on PATH.
  pause
  exit /b 1
)
echo Running...
java -cp out com.hospital.Main
