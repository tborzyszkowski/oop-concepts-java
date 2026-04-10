$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
Push-Location $root

try {
    $out = Join-Path $root 'out'
    if (Test-Path $out) {
        Remove-Item $out -Recurse -Force
    }
    New-Item -ItemType Directory -Path $out | Out-Null

    $sources = Get-ChildItem -Path (Join-Path $root '06-zadania') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
    javac --release 21 -d $out $sources

    java -cp $out exercises.tests.PackageExercisesTest
    Write-Host "Testy zakonczone sukcesem." -ForegroundColor Green
}
finally {
    Pop-Location
}


