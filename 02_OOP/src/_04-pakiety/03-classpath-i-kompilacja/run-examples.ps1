$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$out = Join-Path $root 'out'
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Path $out | Out-Null
# Ustal sciezke do jar.exe
$jarExe = $null
if ($env:JAVA_HOME) {
    $c = Join-Path $env:JAVA_HOME 'bin\jar.exe'
    if (Test-Path $c) { $jarExe = $c }
}
if (-not $jarExe) {
    $guesses = @(
        'C:\Program Files\Java\jdk-23\bin\jar.exe',
        'C:\Program Files\Java\jdk-21\bin\jar.exe',
        (Join-Path $env:ProgramFiles 'OpenJDK\jdk-20\bin\jar.exe')
    )
    foreach ($g in $guesses) {
        if (Test-Path $g) { $jarExe = $g; break }
    }
}
Write-Host "--- 1. Kompilacja ---" -ForegroundColor Cyan
$sources = Get-ChildItem -Path (Join-Path $root 'src') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac --release 21 -d $out $sources
Write-Host "`n--- 2. Uruchomienie z -cp ---" -ForegroundColor Cyan
java -cp $out classpathdemo.app.ClasspathDemo
Write-Host "`n--- 3. Tworzenie JAR ---" -ForegroundColor Cyan
if ($jarExe) {
    $jar = Join-Path $root 'greeting.jar'
    & $jarExe --create --file=$jar --main-class=classpathdemo.app.ClasspathDemo -C $out .
    Write-Host "Utworzono: $jar"
    Write-Host "`n--- 4. Uruchomienie z JAR ---" -ForegroundColor Cyan
    java -jar $jar
} else {
    Write-Host "(Pominieto - jar.exe niedostepny; ustaw JAVA_HOME na katalog JDK)" -ForegroundColor Yellow
}