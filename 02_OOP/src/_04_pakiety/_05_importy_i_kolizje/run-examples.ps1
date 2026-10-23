$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$out = Join-Path $root 'out'
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Path $out | Out-Null

$sources = Get-ChildItem -Path (Join-Path $root 'code') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac --release 21 -d $out $sources

Write-Host "`n--- SimpleImportDemo ---" -ForegroundColor Cyan
java -cp $out _04_pakiety._05_importy_i_kolizje.code.simple.SimpleImportDemo

Write-Host "`n--- StaticImportDemo ---" -ForegroundColor Cyan
java -cp $out _04_pakiety._05_importy_i_kolizje.code.staticdemo.StaticImportDemo

Write-Host "`n--- ImportCollisionDemo (FQN dla Order z dwoch pakietow) ---" -ForegroundColor Cyan
java -cp $out _04_pakiety._05_importy_i_kolizje.code.complex.reporting.ImportCollisionDemo
