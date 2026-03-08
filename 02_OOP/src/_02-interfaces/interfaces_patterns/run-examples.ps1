#!/usr/bin/env pwsh
# run-examples.ps1 — interfaces_patterns

$srcRoot = "$PSScriptRoot\.."
$outDir  = "$srcRoot\out"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null
Set-Location $srcRoot

Write-Host "=== Modul: Interfejsy a Wzorce Projektowe ===" -ForegroundColor Cyan

Write-Host "`n--- [1/4] StrategyDemo (wzorzec Strategy, podmiana w runtime, lambda) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_patterns/StrategyDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_patterns.StrategyDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n--- [2/4] ObserverDemo (wzorzec Observer, EventBus, lambda jako listener) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_patterns/ObserverDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_patterns.ObserverDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n--- [3/4] CommandDemo (wzorzec Command, undo/redo, historia) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_patterns/CommandDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_patterns.CommandDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n--- [4/4] FactoryDemo (wzorzec Factory Method, lambda jako fabryka) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_patterns/FactoryDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_patterns.FactoryDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n=== Koniec modulu interfaces_patterns ===" -ForegroundColor Cyan

