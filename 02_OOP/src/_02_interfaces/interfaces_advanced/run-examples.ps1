#!/usr/bin/env pwsh
# run-examples.ps1 — interfaces_advanced

$srcRoot = "$PSScriptRoot\.."
$outDir  = "$srcRoot\out"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null
Set-Location $srcRoot

Write-Host "=== Modul: Zaawansowane Interfejsy ===" -ForegroundColor Cyan

Write-Host "`n--- [1/3] ComparableDemo (Comparable, Comparator, TreeSet) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_advanced/Product.java interfaces_advanced/ComparableDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_advanced.ComparableDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n--- [2/3] FunctionalDemo (@FunctionalInterface, lambda, method references) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_advanced/Validator.java interfaces_advanced/FunctionalDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_advanced.FunctionalDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n--- [3/3] IterableDemo (Iterable<T>, Iterator<T>, for-each) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_advanced/NumberRange.java interfaces_advanced/IterableDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_advanced.IterableDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n=== Koniec modulu interfaces_advanced ===" -ForegroundColor Cyan

