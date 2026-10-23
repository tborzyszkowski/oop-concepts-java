$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
Push-Location $root

try {
    $out = Join-Path $root 'out'
    if (Test-Path $out) {
        Remove-Item $out -Recurse -Force
    }
    New-Item -ItemType Directory -Path $out | Out-Null

    $sources = Get-ChildItem -Path (Join-Path $root '_06_zadania') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
    javac --release 21 -d $out $sources
    if ($LASTEXITCODE -ne 0) {
        throw "Kompilacja testów nie powiodła się."
    }

    java -cp $out _04_pakiety._06_zadania.code.tests.PackageExercisesTest
    if ($LASTEXITCODE -ne 0) {
        throw "Testy nie przeszły pomyślnie."
    }
    Write-Host "Testy zakonczone sukcesem." -ForegroundColor Green
}
finally {
    Pop-Location
}

