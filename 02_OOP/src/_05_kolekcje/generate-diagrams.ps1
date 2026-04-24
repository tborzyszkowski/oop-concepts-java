$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$repoRoot = Resolve-Path (Join-Path $root "..\..\..")
$candidateJars = @(
    (Join-Path $repoRoot "plantuml.jar"),
    (Join-Path (Join-Path $repoRoot "02_OOP\src") "plantuml.jar")
)

$plantUmlJar = $candidateJars | Where-Object { Test-Path $_ } | Select-Object -First 1
if (-not $plantUmlJar) {
    throw "Nie znaleziono plantuml.jar (sprawdzono: $($candidateJars -join ', '))."
}

$pumlFiles = Get-ChildItem -Path $root -Recurse -Filter "*.puml"
if ($pumlFiles.Count -eq 0) {
    Write-Host "Brak plikow .puml do przetworzenia."
    exit 0
}

foreach ($file in $pumlFiles) {
    Write-Host "Generowanie PNG: $($file.Name)" -ForegroundColor Cyan
    java -jar $plantUmlJar -tpng $file.FullName
}

Write-Host "Wygenerowano PNG dla $($pumlFiles.Count) diagramow." -ForegroundColor Green

