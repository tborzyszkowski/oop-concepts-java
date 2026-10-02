$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$out = Join-Path $root 'out'
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Path $out | Out-Null

$sources = Get-ChildItem -Path (Join-Path $root 'src') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac --release 21 -d $out $sources

Write-Host "`n--- NamespaceCollisionDemo ---" -ForegroundColor Cyan
java -cp $out pakiety.t01.NamespaceCollisionDemo

Write-Host "`n--- ReverseDomainsDemo ---" -ForegroundColor Cyan
java -cp $out pakiety.t01.ReverseDomainsDemo

