$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$outDir = Join-Path $root "out-tests"
if (Test-Path $outDir) { Remove-Item -Recurse -Force $outDir }
New-Item -ItemType Directory -Path $outDir | Out-Null

$junitJar = Resolve-Path (Join-Path $root "..\..\..\junit.jar")

$javaFiles = Get-ChildItem -Path $root -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
javac --release 21 -cp $junitJar -d $outDir $javaFiles

Write-Host "Testy skompilowane." -ForegroundColor Green

$testClasses = @(
    "_05_kolekcje._10_zadania.tests.CollectionsExercisesTest"
)

foreach ($testClass in $testClasses) {
    Write-Host "`n=== $testClass ===" -ForegroundColor Cyan
    java -cp "$outDir;$junitJar" org.junit.runner.JUnitCore $testClass
}

