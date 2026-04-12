#!/usr/bin/env pwsh
# run-examples.ps1 — interfaces_implementation

$srcRoot = "$PSScriptRoot\.."
$outDir  = "$srcRoot\out"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null
Set-Location $srcRoot

Write-Host "=== Modul: Implementacja Interfejsow ===" -ForegroundColor Cyan

Write-Host "`n--- [1/3] MultiInterfaceDemo (wielokrotne implements, polimorfizm) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_implementation/Flyable.java interfaces_implementation/Swimmable.java `
      interfaces_implementation/Airplane.java interfaces_implementation/Duck.java `
      interfaces_implementation/MultiInterfaceDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_implementation.MultiInterfaceDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n--- [2/3] CastingDemo (rzutowanie, pattern matching instanceof) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_implementation/CastingDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_implementation.CastingDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n=== Koniec modulu interfaces_implementation ===" -ForegroundColor Cyan

