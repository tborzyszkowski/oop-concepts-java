#!/usr/bin/env pwsh
# run-lifecycle-examples.ps1
# Kompilacja i uruchomienie przykladow: konstruktory, GC, try-with-resources

$srcRoot = "$PSScriptRoot\..\.."
Set-Location $srcRoot

Write-Host "=== Inicjalizacja i Usuwanie Obiektow ===" -ForegroundColor Cyan
Write-Host ""

Write-Host "--- [1/3] Konstruktory i kolejnosc inicjalizacji (PersonDemo) ---" -ForegroundColor Yellow
javac -d . introduction/object_lifecycle/basic/Person.java introduction/object_lifecycle/basic/PersonDemo.java
if ($LASTEXITCODE -eq 0) {
    java introduction.object_lifecycle.basic.PersonDemo
}

Write-Host ""
Write-Host "--- [2/3] Garbage Collector (GcDemo) ---" -ForegroundColor Yellow
javac -d . introduction/object_lifecycle/advanced/GcDemo.java
if ($LASTEXITCODE -eq 0) {
    java introduction.object_lifecycle.advanced.GcDemo
}

Write-Host ""
Write-Host "--- [3/3] AutoCloseable + try-with-resources (ResourceHolder) ---" -ForegroundColor Yellow
javac -d . introduction/object_lifecycle/advanced/ResourceHolder.java
if ($LASTEXITCODE -eq 0) {
    java introduction.object_lifecycle.advanced.ResourceHolder
}

Write-Host ""
Write-Host "=== Koniec ===" -ForegroundColor Cyan
