$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$repoRoot = Resolve-Path (Join-Path $root "..\..\..")
$junitJar = Join-Path $repoRoot "junit.jar"
if (-not (Test-Path $junitJar)) {
    throw "Nie znaleziono pliku junit.jar w katalogu repozytorium."
}

$outDir = Join-Path $root "out-tests"
if (Test-Path $outDir) { Remove-Item -Recurse -Force $outDir }
New-Item -ItemType Directory -Path $outDir | Out-Null

$javaFiles = Get-ChildItem -Path $root -Recurse -Filter "*.java"
if ($javaFiles.Count -eq 0) {
    Write-Host "Brak plikow Java do testow."
    exit 0
}

javac --release 21 -cp $junitJar -d $outDir $javaFiles.FullName

java -jar $junitJar --class-path $outDir --scan-class-path

