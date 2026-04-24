$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$out = Join-Path $root "out"
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Path $out | Out-Null

$src = Join-Path $root "code"
$sources = Get-ChildItem -Path $src -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
javac --release 21 -d $out $sources

Write-Host "`n--- CollectionsIntroDemo ---" -ForegroundColor Cyan
java -cp $out _05_kolekcje._01_kolekcje_intro.code.CollectionsIntroDemo

