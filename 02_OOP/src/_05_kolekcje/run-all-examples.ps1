$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$outDir = Join-Path $root "out"
if (Test-Path $outDir) { Remove-Item -Recurse -Force $outDir }
New-Item -ItemType Directory -Path $outDir | Out-Null

$javaFiles = Get-ChildItem -Path $root -Recurse -Filter "*.java" |
    Where-Object { $_.FullName -notmatch "\\tests\\" -and $_.FullName -notmatch "\\_10_zadania\\tasks\\" }

if ($javaFiles.Count -eq 0) {
    Write-Host "Brak plikow Java do kompilacji."
    exit 0
}

javac --release 21 -d $outDir $javaFiles.FullName

$mainClasses = @(
    "_05_kolekcje._01_kolekcje_intro.code.CollectionsIntroDemo",
    "_05_kolekcje._02_typy_kolekcji.code.CollectionTypesDemo",
    "_05_kolekcje._03_typy_generyczne.code.GenericsDemo",
    "_05_kolekcje._04_set_hashset_treeset.code.SetComparisonDemo",
    "_05_kolekcje._05_iteratory.code.IteratorDemo",
    "_05_kolekcje._06_mapy.code.MapDemo",
    "_05_kolekcje._07_komparatory.code.ComparatorDemo",
    "_05_kolekcje._08_strumienie.code.StreamsDemo",
    "_05_kolekcje._09_projekt.code.AnagramSolver"
)

foreach ($mainClass in $mainClasses) {
    Write-Host "`n=== $mainClass ===" -ForegroundColor Cyan
    java -cp $outDir $mainClass
}

Write-Host "`nWszystkie przykłady uruchomione." -ForegroundColor Green

