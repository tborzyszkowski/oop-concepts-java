$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$out = Join-Path $root 'out'
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Path $out | Out-Null

$sources = Get-ChildItem -Path (Join-Path $root 'src') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac --release 21 -d $out $sources

Write-Host "`n--- SamePackageDemo (widzi package-private) ---" -ForegroundColor Cyan
java -cp $out access.core.SamePackageDemo

Write-Host "`n--- FriendInspector (podklasa z innego pakietu) ---" -ForegroundColor Cyan
java -cp $out access.friends.FriendInspector

Write-Host "`n--- AccessControlDemo (klient z zewnatrz) ---" -ForegroundColor Cyan
java -cp $out access.outsiders.AccessControlDemo

