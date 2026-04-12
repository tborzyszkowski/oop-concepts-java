#!/usr/bin/env pwsh
# run-examples.ps1 — interfaces_special

$srcRoot = "$PSScriptRoot\.."
$outDir  = "$srcRoot\out"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null
Set-Location $srcRoot

Write-Host "=== Modul: Szczegolne Wlasnosci Interfejsow ===" -ForegroundColor Cyan

Write-Host "`n--- [1/2] DefaultMethodDemo (stale, default, static, private, Diamond Problem) ---" -ForegroundColor Yellow
javac -d "$outDir" interfaces_special/Vehicle.java interfaces_special/ElectricVehicle.java `
      interfaces_special/Tesla.java interfaces_special/DefaultMethodDemo.java
if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_special.DefaultMethodDemo }
else { Write-Host "Blad kompilacji!" -ForegroundColor Red }

Write-Host "`n--- [2/2] SealedDemo (sealed interface, record, switch pattern matching Java 21) ---" -ForegroundColor Yellow
javac --enable-preview --release 21 -d "$outDir" interfaces_special/SealedDemo.java
if ($LASTEXITCODE -eq 0) {
  java --enable-preview -cp "$outDir" interfaces_special.SealedDemo
} else {
  # Fallback bez preview (Java 21 ma to juz stabilne)
  javac -d "$outDir" interfaces_special/SealedDemo.java
  if ($LASTEXITCODE -eq 0) { java -cp "$outDir" interfaces_special.SealedDemo }
  else { Write-Host "Blad kompilacji!" -ForegroundColor Red }
}

Write-Host "`n=== Koniec modulu interfaces_special ===" -ForegroundColor Cyan

