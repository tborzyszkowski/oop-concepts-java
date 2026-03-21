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
    "inheritance.t01.InheritanceIntroDemo",
    "inheritance.t02.TypeCompatibilityDemo",
    "inheritance.t03.SuperKeywordDemo",
    "inheritance.t04.OverrideVsOverloadDemo",
    "inheritance.t05.DynamicBindingDemo",
    "inheritance.t06.PolymorphismPatternsDemo",
    "inheritance.t07.AbstractVsInterfaceDemo",
    "inheritance.t08.DiAbstractionDemo",
    "inheritance.t09.FinalKeywordDemo",
    "inheritance.t10.ObjectClassDemo",
    "inheritance.t11.JavadocDemo",
    "inheritance.t12.TasksDemo"
)

foreach ($mainClass in $mainClasses) {
    Write-Host "`n=== $mainClass ==="
    java -cp $outDir $mainClass
}

