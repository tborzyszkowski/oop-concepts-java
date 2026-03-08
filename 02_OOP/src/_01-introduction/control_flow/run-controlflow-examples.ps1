#!/usr/bin/env pwsh
# run-controlflow-examples.ps1
# Kompilacja i uruchomienie przykladow instrukcji sterujacych

$srcRoot = "$PSScriptRoot\.."
$outDir  = "$srcRoot\out"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null
Set-Location $srcRoot

Write-Host "=== Instrukcje Sterujace Java ===" -ForegroundColor Cyan
Write-Host ""

Write-Host "--- [1/3] Instrukcje warunkowe (if/else, switch) ---" -ForegroundColor Yellow
javac -d "$outDir" control_flow/examples/ConditionalsDemo.java
if ($LASTEXITCODE -eq 0) {
    java -cp "$outDir" introduction.control_flow.examples.ConditionalsDemo
}

Write-Host ""
Write-Host "--- [2/3] Petle (for, while, do-while, for-each) ---" -ForegroundColor Yellow
javac -d "$outDir" control_flow/examples/LoopsDemo.java
if ($LASTEXITCODE -eq 0) {
    java -cp "$outDir" introduction.control_flow.examples.LoopsDemo
}

Write-Host ""
Write-Host "--- [3/3] Pattern Matching w switch (Java 21) ---" -ForegroundColor Yellow
javac --release 21 -d "$outDir" control_flow/examples/SwitchPatternDemo.java
if ($LASTEXITCODE -eq 0) {
    java -cp "$outDir" introduction.control_flow.examples.SwitchPatternDemo
}

Write-Host ""
Write-Host "=== Koniec ===" -ForegroundColor Cyan
