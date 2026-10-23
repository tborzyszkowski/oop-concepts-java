$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$out = Join-Path $root 'out'
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Path $out | Out-Null

$sources = Get-ChildItem -Path (Join-Path $root 'code') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac --release 21 -d $out $sources

Write-Host "`n--- SamePackageDemo (widzi package-private) ---" -ForegroundColor Cyan
java -cp $out _04_pakiety._04_kontrola_dostepu.code.core.SamePackageDemo

Write-Host "`n--- FriendInspector (podklasa z innego pakietu) ---" -ForegroundColor Cyan
java -cp $out _04_pakiety._04_kontrola_dostepu.code.friends.FriendInspector

Write-Host "`n--- AccessControlDemo (klient z zewnatrz) ---" -ForegroundColor Cyan
java -cp $out _04_pakiety._04_kontrola_dostepu.code.outsiders.AccessControlDemo
