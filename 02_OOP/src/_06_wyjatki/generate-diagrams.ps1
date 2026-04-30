# PowerShell — generuje diagramy PNG ze wszystkich plików .puml w _06_wyjatki
$plantuml = Join-Path (Split-Path -Parent $MyInvocation.MyCommand.Path) "..\..\plantuml.jar"
$plantuml = (Resolve-Path $plantuml -ErrorAction SilentlyContinue)?.Path

if (-not $plantuml) {
    $plantuml = "C:\home\gitHub\oop-concepts-java\plantuml.jar"
}

Write-Host "PlantUML jar: $plantuml" -ForegroundColor Cyan

$pumlFiles = Get-ChildItem (Split-Path -Parent $MyInvocation.MyCommand.Path) -Recurse -Filter "*.puml"
foreach ($f in $pumlFiles) {
    Write-Host "  Generuję: $($f.Name)" -ForegroundColor Yellow
    java -jar $plantuml $f.FullName
    if ($LASTEXITCODE -ne 0) {
        Write-Host "  BŁĄD: $($f.Name)" -ForegroundColor Red
    }
}

Write-Host "Gotowe — wygenerowano $($pumlFiles.Count) diagramów." -ForegroundColor Green

