$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$plantUmlJar = Join-Path (Split-Path -Parent $root) 'plantuml.jar'

if (-not (Test-Path $plantUmlJar)) {
    throw "Nie znaleziono plantuml.jar pod: $plantUmlJar"
}

$allPuml = Get-ChildItem -Path $root -Recurse -Filter *.puml | ForEach-Object { $_.FullName }
if ($allPuml.Count -eq 0) {
    Write-Host 'Brak plików .puml do kompilacji.' -ForegroundColor Yellow
    exit 0
}

java -jar $plantUmlJar -tpng @allPuml
Write-Host "Wygenerowano diagramy PNG: $($allPuml.Count) plik(ów) .puml." -ForegroundColor Green

