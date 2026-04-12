#!/usr/bin/env pwsh
# run-fields-examples.ps1
# Kompilacja i uruchomienie przykladow: pola, metody, static, enkapsulacja

$srcRoot = "$PSScriptRoot\.."
$outDir  = "$srcRoot\out"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null
Set-Location $srcRoot

Write-Host "=== Pola i Metody - modyfikatory dostepu, static, enkapsulacja ===" -ForegroundColor Cyan
Write-Host ""

Write-Host "--- [1/2] PRZED - anty-wzorzec (publiczne pola) ---" -ForegroundColor Red
javac -d "$outDir" fields_and_methods/before/*.java
if ($LASTEXITCODE -eq 0) {
    java -cp "$outDir" introduction.fields_and_methods.before.CounterBeforeDemo
}

Write-Host ""

Write-Host "--- [2/2] PO - enkapsulacja, pola statyczne, MathUtils ---" -ForegroundColor Green
javac -d "$outDir" fields_and_methods/after/*.java
if ($LASTEXITCODE -eq 0) {
    java -cp "$outDir" introduction.fields_and_methods.after.FieldsMethodsDemo
}

Write-Host ""
Write-Host "=== Koniec ===" -ForegroundColor Cyan
