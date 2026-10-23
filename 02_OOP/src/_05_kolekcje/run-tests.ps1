$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$outDir = Join-Path $root "out-tests"
if (Test-Path $outDir) { Remove-Item -Recurse -Force $outDir }
New-Item -ItemType Directory -Path $outDir | Out-Null

$javaFiles = Get-ChildItem -Path (Join-Path $root "_10_zadania\solutions") -Filter "*.java" |
    ForEach-Object { $_.FullName }
javac --release 21 -d $outDir $javaFiles
if ($LASTEXITCODE -ne 0) {
    throw "Kompilacja rozwiązań nie powiodła się."
}

Write-Host "Rozwiązania skompilowane." -ForegroundColor Green
java -cp $outDir _05_kolekcje._10_zadania.solutions.CollectionsExercisesSolutions
if ($LASTEXITCODE -ne 0) {
    throw "Weryfikacja rozwiązań nie powiodła się."
}
