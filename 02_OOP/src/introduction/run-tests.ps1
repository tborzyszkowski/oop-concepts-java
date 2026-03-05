# run-tests.ps1
# Uruchamia wszystkie testy JUnit dla modulu introduction

# $PSScriptRoot = .../02_OOP/src/introduction
$srcRoot  = "$PSScriptRoot\.."          # .../02_OOP/src
$junitJar = "$PSScriptRoot\..\..\..\junit.jar"  # .../oop-concepts-java/junit.jar
Set-Location $srcRoot

if (-not (Test-Path $junitJar)) {
    Write-Host "BRAK junit.jar! Pobierz z:" -ForegroundColor Red
    Write-Host "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.0/junit-platform-console-standalone-1.11.0.jar"
    Write-Host "i zapisz jako: $junitJar"
    exit 1
}

Write-Host "=== Testy jednostkowe - Wprowadzenie do OOP ===" -ForegroundColor Cyan
Write-Host "JUnit: $junitJar" -ForegroundColor Gray
Write-Host ""

$totalPass = 0
$totalFail = 0

function Run-Tests($label, $sources, $testClass) {
    Write-Host "--- $label ---" -ForegroundColor Yellow
    # Kompilacja
    $compileArgs = @("-cp", ".;$junitJar", "-d", ".") + $sources
    $err = javac @compileArgs 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Host "  BLAD KOMPILACJI:" -ForegroundColor Red
        $err | ForEach-Object { Write-Host "    $_" -ForegroundColor Red }
        return
    }
    # Uruchomienie — przez cmd.exe by uniknac NativeCommandError w PS 5.1
    $tmpOut = [System.IO.Path]::GetTempFileName()
    cmd /c "java -cp `".;$junitJar`" org.junit.platform.console.ConsoleLauncher `"--select-class=$testClass`" > `"$tmpOut`" 2>&1"
    $result = Get-Content $tmpOut -Encoding UTF8
    Remove-Item $tmpOut -ErrorAction SilentlyContinue

    $passLine = $result | Where-Object { $_ -match "tests successful" } | Select-Object -First 1
    $failLine = $result | Where-Object { $_ -match "tests failed" }     | Select-Object -First 1
    $passed   = if ($passLine) { [regex]::Match($passLine, '\d+').Value } else { "?" }
    $failed   = if ($failLine) { [regex]::Match($failLine, '\d+').Value } else { "0" }

    if ([int]$failed -gt 0) {
        Write-Host "  FAILED: $failed  passed: $passed" -ForegroundColor Red
        $result | Where-Object { $_ -match "FAILED|AssertionError" } |
            ForEach-Object { Write-Host "    $_" -ForegroundColor Red }
    } else {
        Write-Host "  OK: $passed tests passed" -ForegroundColor Green
    }
}

Run-Tests "PersonTest (konstruktory, walidacja)" @(
    "introduction/object_lifecycle/basic/Person.java",
    "introduction/object_lifecycle/tests/PersonTest.java"
) "introduction.object_lifecycle.tests.PersonTest"

Run-Tests "CopyTest (shallow vs deep copy)" @(
    "introduction/object_lifecycle/copies/CopyDemo.java",
    "introduction/object_lifecycle/tests/CopyTest.java"
) "introduction.object_lifecycle.tests.CopyTest"

Run-Tests "CounterAndMathTest (pola statyczne, MathUtils)" @(
    "introduction/fields_and_methods/after/Counter.java",
    "introduction/fields_and_methods/after/MathUtils.java",
    "introduction/fields_and_methods/tests/CounterAndMathTest.java"
) "introduction.fields_and_methods.tests.CounterAndMathTest"

Run-Tests "StackTest (TDD - Red/Green/Refactor)" @(
    "introduction/tdd/src/Stack.java",
    "introduction/tdd/tests/StackTest.java"
) "introduction.tdd.tests.StackTest"

Write-Host ""
Write-Host "=== Koniec testow ===" -ForegroundColor Cyan

