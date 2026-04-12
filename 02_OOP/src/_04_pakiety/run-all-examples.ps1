$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path

$modules = @(
    '_01_przestrzenie_nazw_i_pakiety',
    '_02_definicja_pakietu_i_struktura',
    '_03_classpath_i_kompilacja',
    '_04_kontrola_dostepu',
    '_05_importy_i_kolizje',
    '_06_zadania'
)

foreach ($module in $modules) {
    Write-Host "`n=== $module ===" -ForegroundColor Cyan
    & (Join-Path $root "$module\run-examples.ps1")
}

Write-Host "`nWszystkie przykłady uruchomione." -ForegroundColor Green


