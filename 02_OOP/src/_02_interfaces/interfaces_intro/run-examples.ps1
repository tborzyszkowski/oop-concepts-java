#!/usr/bin/env pwsh
# run-examples.ps1 — interfaces_intro
# Uruchomienie z katalogu _02_interfaces:  .\interfaces_intro\run-examples.ps1

$srcRoot = "$PSScriptRoot\.."
$outDir  = "$srcRoot\out"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null
Set-Location $srcRoot

Write-Host "=== Modul: Pojecie Interfejsu ===" -ForegroundColor Cyan

Write-Host "`n--- [1/2] IntroDemo (polimorfizm, instanceof, zmienna interfejsowa) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_intro/Printable.java interfaces_intro/Document.java interfaces_intro/Photo.java interfaces_intro/IntroDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_intro.IntroDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n--- [2/2] ContractDemo (interfejs jako kontrakt dla roznych hierarchii) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_intro/ContractDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_intro.ContractDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n=== Koniec modulu interfaces_intro ===" -ForegroundColor Cyan

