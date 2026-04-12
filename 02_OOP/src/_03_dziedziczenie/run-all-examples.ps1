$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$outDir = Join-Path $root "out"
if (Test-Path $outDir) { Remove-Item -Recurse -Force $outDir }
New-Item -ItemType Directory -Path $outDir | Out-Null

$javaFiles = Get-ChildItem -Path $root -Recurse -Filter "*.java" |
    Where-Object { $_.FullName -notmatch "\\tests\\" }

if ($javaFiles.Count -eq 0) {
    Write-Host "Brak plikow Java do kompilacji."
    exit 0
}

javac --release 21 -d $outDir $javaFiles.FullName

$mainClasses = @(
    "_03_dziedziczenie._01_dziedziczenie_intro.code.InheritanceIntroDemo",
    "_03_dziedziczenie._02_zgodnosc_typow.code.TypeCompatibilityDemo",
    "_03_dziedziczenie._03_super.code.SuperKeywordDemo",
    "_03_dziedziczenie._04_override_vs_overload.code.OverrideVsOverloadDemo",
    "_03_dziedziczenie._05_wiazanie_dynamiczne.code.DynamicBindingDemo",
    "_03_dziedziczenie._06_polimorfizm_i_wzorce.code.PolymorphismPatternsDemo",
    "_03_dziedziczenie._07_abstrakcyjne_vs_interfejsy.code.AbstractVsInterfaceDemo",
    "_03_dziedziczenie._08_di_i_abstrakcje.code.DiAbstractionDemo",
    "_03_dziedziczenie._09_final.code.FinalKeywordDemo",
    "_03_dziedziczenie._10_object.code.ObjectClassDemo",
    "_03_dziedziczenie._11_javadoc.code.JavadocDemo",
    "_03_dziedziczenie._12_zadania.code.TasksDemo"
)

foreach ($mainClass in $mainClasses) {
    Write-Host "`n=== $mainClass ==="
    java -cp $outDir $mainClass
}

