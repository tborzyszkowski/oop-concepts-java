#!/usr/bin/env pwsh
# run-all-examples.ps1 — uruchamia wszystkie podmoduły _02_interfaces

$moduleRoot = $PSScriptRoot
Write-Host "╔══════════════════════════════════════════════╗" -ForegroundColor Magenta
Write-Host "║  Modul 02 — Interfejsy w Javie               ║" -ForegroundColor Magenta
Write-Host "╚══════════════════════════════════════════════╝" -ForegroundColor Magenta

$scripts = @(
    "interfaces_intro\run-examples.ps1",
    "interfaces_implementation\run-examples.ps1",
    "interfaces_advanced\run-examples.ps1",
    "interfaces_special\run-examples.ps1",
    "interfaces_patterns\run-examples.ps1"
)

foreach ($script in $scripts) {
    $full = Join-Path $moduleRoot $script
    if (Test-Path $full) {
        Write-Host ("`n" + "=" * 50) -ForegroundColor DarkCyan
        & $full
    } else {
        Write-Host "Brak skryptu: $full" -ForegroundColor Red
    }
}

Write-Host "`n╔══════════════════════════════════════════════╗" -ForegroundColor Magenta
Write-Host "║  Koniec wszystkich przykladow                ║" -ForegroundColor Magenta
Write-Host "╚══════════════════════════════════════════════╝" -ForegroundColor Magenta

