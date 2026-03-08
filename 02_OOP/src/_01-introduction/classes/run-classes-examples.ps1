#!/usr/bin/env pwsh
# run-classes-examples.ps1
# Kompilacja i uruchomienie przykladow kodu dla tematu "Klasy i Obiekty"

# srcRoot = 01-introduction/  — korzeń kompilacji; pakiety (introduction.*) sa zadeklarowane w plikach java
$srcRoot = "$PSScriptRoot\.."
$outDir  = "$srcRoot\out"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null
Set-Location $srcRoot

Write-Host "=== Klasy i Obiekty w Javie ===" -ForegroundColor Cyan
Write-Host ""

# --- Przyklad podstawowy: Dog ---
Write-Host "--- [1/2] Przyklad podstawowy: Dog (ClassesDemo) ---" -ForegroundColor Yellow
Write-Host "Kompilacja..." -ForegroundColor Gray

javac -d "$outDir" classes/basic/Dog.java classes/basic/ClassesDemo.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Uruchamianie ClassesDemo..." -ForegroundColor Green
    java -cp "$outDir" introduction.classes.basic.ClassesDemo
} else {
    Write-Host "Blad kompilacji!" -ForegroundColor Red
}

Write-Host ""

# --- Przyklad zaawansowany: BankAccount ---
Write-Host "--- [2/2] Przyklad zaawansowany: BankAccount ---" -ForegroundColor Yellow
Write-Host "Kompilacja..." -ForegroundColor Gray

javac -d "$outDir" classes/advanced/BankAccount.java classes/advanced/BankAccountDemo.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Uruchamianie BankAccountDemo..." -ForegroundColor Green
    java -cp "$outDir" introduction.classes.advanced.BankAccountDemo
} else {
    Write-Host "Blad kompilacji!" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== Koniec demonstracji ===" -ForegroundColor Cyan
