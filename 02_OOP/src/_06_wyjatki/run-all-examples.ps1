# PowerShell — uruchamia wszystkie przykłady z modułu _06_wyjatki
$ErrorActionPreference = "Continue"
$src = Split-Path -Parent $MyInvocation.MyCommand.Path
$root = Join-Path $src "out"

Write-Host "=== Kompilacja modułu _06_wyjatki ===" -ForegroundColor Cyan
New-Item -ItemType Directory -Force -Path $root | Out-Null

$files = @(
    "_01_wprowadzenie/code/ExceptionIntroDemo.java",
    "_02_hierarchia_klas/code/ExceptionHierarchyDemo.java",
    "_03_throw_catch/code/ThrowCatchDemo.java",
    "_04_zagniezdzony_try/code/NestedTryDemo.java",
    "_05_throw_rethrow/code/ThrowRethrowDemo.java",
    "_06_throws/code/ThrowsDeclarationDemo.java",
    "_07_finally/code/FinallyDemo.java",
    "_08_wlasne_wyjatki/code/CustomExceptionsDemo.java",
    "_09_projekt/code/BankApp.java",
    "_10_zadania/solutions/ExceptionExercisesSolutions.java"
)

foreach ($f in $files) {
    $full = Join-Path $src $f
    Write-Host "  Kompilacja: $f" -ForegroundColor Yellow
    javac -d $root $full
    if ($LASTEXITCODE -ne 0) {
        Write-Host "  BŁĄD kompilacji: $f" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "=== Uruchamianie przykładów ===" -ForegroundColor Cyan

$classes = @(
    "_06_wyjatki._01_wprowadzenie.code.ExceptionIntroDemo",
    "_06_wyjatki._02_hierarchia_klas.code.ExceptionHierarchyDemo",
    "_06_wyjatki._03_throw_catch.code.ThrowCatchDemo",
    "_06_wyjatki._04_zagniezdzony_try.code.NestedTryDemo",
    "_06_wyjatki._05_throw_rethrow.code.ThrowRethrowDemo",
    "_06_wyjatki._06_throws.code.ThrowsDeclarationDemo",
    "_06_wyjatki._07_finally.code.FinallyDemo",
    "_06_wyjatki._08_wlasne_wyjatki.code.CustomExceptionsDemo",
    "_06_wyjatki._09_projekt.code.BankApp",
    "_06_wyjatki._10_zadania.solutions.ExceptionExercisesSolutions"
)

foreach ($cls in $classes) {
    Write-Host ""
    Write-Host "--- $cls ---" -ForegroundColor Green
    java -cp $root $cls
}

Write-Host ""
Write-Host "=== Gotowe ===" -ForegroundColor Cyan

