#!/usr/bin/env pwsh
# run-all-examples.ps1
# Uruchamia wszystkie przykłady z modułu 01-introduction

$introRoot = Split-Path -Parent $MyInvocation.MyCommand.Path

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  Wprowadzenie do OOP w Javie - wszystkie  " -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

$scripts = @(
    "$introRoot\classes\run-classes-examples.ps1",
    "$introRoot\fields_and_methods\run-fields-examples.ps1",
    "$introRoot\object_lifecycle\run-lifecycle-examples.ps1",
    "$introRoot\control_flow\run-controlflow-examples.ps1"
)

$names = @("classes", "fields_and_methods", "object_lifecycle", "control_flow")

for ($i = 0; $i -lt $scripts.Length; $i++) {
    Write-Host ""
    Write-Host "############################################" -ForegroundColor Magenta
    Write-Host "  MODUL: $($names[$i])" -ForegroundColor Magenta
    Write-Host "############################################" -ForegroundColor Magenta
    & $scripts[$i]
}

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  Koniec wszystkich modulow                " -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
