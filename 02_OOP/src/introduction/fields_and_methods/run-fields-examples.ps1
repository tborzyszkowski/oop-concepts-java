#!/usr/bin/env pwsh
# run-fields-examples.ps1
# Kompilacja i uruchomienie przykladow: pola, metody, static, enkapsulacja

$srcRoot = "$PSScriptRoot\..\.."
Set-Location $srcRoot

Write-Host "=== Pola i Metody - modyfikatory dostepu, static, enkapsulacja ===" -ForegroundColor Cyan
Write-Host ""

# --- Anty-wzorzec: publiczne pola ---
Write-Host "--- [1/2] PRZED - anty-wzorzec (publiczne pola) ---" -ForegroundColor Red
javac -d . introduction/fields_and_methods/before/*.java
if ($LASTEXITCODE -eq 0) {
    java introduction.fields_and_methods.before.CounterBeforeDemo
}

Write-Host ""

# --- Enkapsulacja: prywatne pola + metody statyczne ---
Write-Host "--- [2/2] PO - enkapsulacja, pola statyczne, MathUtils ---" -ForegroundColor Green
javac -d . introduction/fields_and_methods/after/*.java
if ($LASTEXITCODE -eq 0) {
    java introduction.fields_and_methods.after.FieldsMethodsDemo
}

Write-Host ""
Write-Host "=== Koniec ===" -ForegroundColor Cyan
