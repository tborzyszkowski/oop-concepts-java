#!/usr/bin/env pwsh
# run-lifecycle-examples.ps1
# Kompilacja i uruchomienie przykladow: konstruktory, kopie, GC, try-with-resources

$srcRoot = "$PSScriptRoot\.."
$outDir  = "$srcRoot\out"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null
Set-Location $srcRoot

Write-Host "=== Inicjalizacja i Usuwanie Obiektow ===" -ForegroundColor Cyan
Write-Host ""

Write-Host "--- [1/5] Konstruktory i kolejnosc inicjalizacji (PersonDemo) ---" -ForegroundColor Yellow
javac -d "$outDir" object_lifecycle/basic/Person.java object_lifecycle/basic/PersonDemo.java
if ($LASTEXITCODE -eq 0) {
    java -cp "$outDir" introduction.object_lifecycle.basic.PersonDemo
}

Write-Host ""
Write-Host "--- [2/5] Kopia platka vs Gleboka (CopyDemo) ---" -ForegroundColor Yellow
javac -d "$outDir" object_lifecycle/copies/CopyDemo.java
if ($LASTEXITCODE -eq 0) {
    java -cp "$outDir" introduction.object_lifecycle.copies.CopyDemo
}

Write-Host ""
Write-Host "--- [3/5] Wzorzec Prototype (PrototypeDemo) ---" -ForegroundColor Yellow
javac -d "$outDir" object_lifecycle/copies/PrototypeDemo.java
if ($LASTEXITCODE -eq 0) {
    java -cp "$outDir" introduction.object_lifecycle.copies.PrototypeDemo
}

Write-Host ""
Write-Host "--- [4/5] Garbage Collector (GcDemo) ---" -ForegroundColor Yellow
javac -d "$outDir" object_lifecycle/advanced/GcDemo.java
if ($LASTEXITCODE -eq 0) {
    java -cp "$outDir" introduction.object_lifecycle.advanced.GcDemo
}

Write-Host ""
Write-Host "--- [5/5] AutoCloseable + try-with-resources (ResourceHolder) ---" -ForegroundColor Yellow
javac -d "$outDir" object_lifecycle/advanced/ResourceHolder.java
if ($LASTEXITCODE -eq 0) {
    java -cp "$outDir" introduction.object_lifecycle.advanced.ResourceHolder
}

Write-Host ""
Write-Host "=== Koniec ===" -ForegroundColor Cyan
