$ErrorActionPreference = "Stop"

$moduleRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$srcRoot = Join-Path $moduleRoot "src"
$outRoot = Join-Path $moduleRoot "out-docs"

if (Test-Path $outRoot) {
    Remove-Item -Recurse -Force $outRoot
}
New-Item -ItemType Directory -Path $outRoot | Out-Null

$common = @(
    "-sourcepath", $srcRoot,
    "-subpackages", "inheritance.t11",
    "-encoding", "UTF-8",
    "-charset", "UTF-8",
    "-docencoding", "UTF-8"
)

$javadocCmd = Get-Command javadoc -ErrorAction SilentlyContinue
if ($javadocCmd) {
    $javadocExe = $javadocCmd.Source
} else {
    $candidates = @()

    if ($env:JAVA_HOME) {
        $candidates += (Join-Path $env:JAVA_HOME "bin\javadoc.exe")
    }

    $javacCmd = Get-Command javac -ErrorAction SilentlyContinue
    if ($javacCmd) {
        $candidates += (Join-Path (Split-Path -Parent $javacCmd.Source) "javadoc.exe")
    }

    $candidates += Get-ChildItem -Path "C:\Program Files\Java" -Recurse -Filter "javadoc.exe" -ErrorAction SilentlyContinue |
        Select-Object -ExpandProperty FullName

    $javadocExe = $candidates | Where-Object { Test-Path $_ } | Select-Object -First 1
    if (-not $javadocExe) {
        throw "Nie znaleziono javadoc.exe. Ustaw JAVA_HOME na katalog JDK (np. C:\\Program Files\\Java\\jdk-21)."
    }
}

& $javadocExe @common -d (Join-Path $outRoot "public") -public
if ($LASTEXITCODE -ne 0) { throw "Generowanie Javadoc nie powiodlo sie dla profilu: public (exit code $LASTEXITCODE)." }

& $javadocExe @common -d (Join-Path $outRoot "protected") -protected
if ($LASTEXITCODE -ne 0) { throw "Generowanie Javadoc nie powiodlo sie dla profilu: protected (exit code $LASTEXITCODE)." }

& $javadocExe @common -d (Join-Path $outRoot "private") -private
if ($LASTEXITCODE -ne 0) { throw "Generowanie Javadoc nie powiodlo sie dla profilu: private (exit code $LASTEXITCODE)." }

& $javadocExe @common -d (Join-Path $outRoot "linksource") -public -linksource -Xdoclint:all
if ($LASTEXITCODE -ne 0) { throw "Generowanie Javadoc nie powiodlo sie dla profilu: linksource (exit code $LASTEXITCODE)." }

& $javadocExe @common -d (Join-Path $outRoot "draft") -public -Xdoclint:none
if ($LASTEXITCODE -ne 0) { throw "Generowanie Javadoc nie powiodlo sie dla profilu: draft (exit code $LASTEXITCODE)." }

Write-Host "Wygenerowano Javadoc w katalogach: public, protected, private, linksource, draft."



