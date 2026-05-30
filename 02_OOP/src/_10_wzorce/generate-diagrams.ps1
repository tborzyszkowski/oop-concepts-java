# PowerShell - generuje diagramy PNG ze wszystkich plikow .puml w _10_wzorce
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$candidate = Join-Path $scriptDir "..\..\plantuml.jar"

$resolved = Resolve-Path $candidate -ErrorAction SilentlyContinue
if ($resolved) {
    $plantuml = $resolved.Path
} else {
    $plantuml = "C:\home\gitHub\oop-concepts-java\plantuml.jar"
}

if (-not (Test-Path $plantuml)) {
    Write-Host "BLAD: Nie znaleziono plantuml.jar" -ForegroundColor Red
    exit 1
}

Write-Host "PlantUML jar: $plantuml" -ForegroundColor Cyan

$pumlFiles = Get-ChildItem $scriptDir -Recurse -Filter "*.puml"
foreach ($f in $pumlFiles) {
    Write-Host "  Generuje: $($f.Name)" -ForegroundColor Yellow
    java -jar $plantuml $f.FullName
    if ($LASTEXITCODE -ne 0) {
        Write-Host "  BLAD: $($f.Name)" -ForegroundColor Red
    }
}

Write-Host "Gotowe - wygenerowano $($pumlFiles.Count) diagramow." -ForegroundColor Green

