$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$out = Join-Path $root "out"
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Path $out | Out-Null

$sources = Get-ChildItem -Path (Join-Path $root "code") -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
javac --release 21 -d $out $sources

Write-Host "`n--- SetComparisonDemo ---" -ForegroundColor Cyan
java -cp $out _05_kolekcje._04_set_hashset_treeset.code.SetComparisonDemo

