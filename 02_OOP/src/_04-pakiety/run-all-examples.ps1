$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path

$modules = @(
    '01-przestrzenie-nazw-i-pakiety',
    '02-definicja-pakietu-i-struktura',
    '03-classpath-i-kompilacja',
    '04-kontrola-dostepu',
    '05-importy-i-kolizje',
    '06-zadania'
)

foreach ($module in $modules) {
    Write-Host "`n=== $module ===" -ForegroundColor Cyan
    & (Join-Path $root "$module\run-examples.ps1")
}

Write-Host "`nWszystkie przykłady uruchomione." -ForegroundColor Green


