# Test All SOLID Examples
# PowerShell script to compile and run all SOLID examples

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   SOLID Examples - Automated Testing   " -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Navigate to the src directory
Set-Location "C:\home\gitHub\oop-concepts-java\03_ADVANCED\src"

$totalTests = 0
$passedTests = 0
$failedTests = 0

function Test-Example {
    param(
        [string]$Name,
        [string]$CompilePath,
        [string]$RunClass
    )

    $global:totalTests++
    Write-Host "`n[$global:totalTests] Testing: $Name" -ForegroundColor Yellow
    Write-Host ("=" * 60) -ForegroundColor Gray

    try {
        # Compile
        Write-Host "Compiling..." -ForegroundColor Gray
        if ($CompilePath -like "*.java") {
            javac $CompilePath 2>&1 | Out-Null
        } else {
            javac "$CompilePath/*.java" 2>&1 | Out-Null
        }

        if ($LASTEXITCODE -ne 0) {
            throw "Compilation failed"
        }

        # Run
        Write-Host "Running..." -ForegroundColor Gray
        java $RunClass

        if ($LASTEXITCODE -eq 0) {
            $global:passedTests++
            Write-Host "`n✅ PASSED: $Name" -ForegroundColor Green
        } else {
            throw "Runtime error"
        }
    }
    catch {
        $global:failedTests++
        Write-Host "`n❌ FAILED: $Name - $_" -ForegroundColor Red
    }
}

# Test Simple Examples (no packages)
Write-Host "`n### SIMPLE EXAMPLES (No Packages) ###" -ForegroundColor Magenta
Set-Location "solid"

Test-Example -Name "SRP - Simple Demo" `
    -CompilePath "SimpleSrpDemo.java" `
    -RunClass "SimpleSrpDemo"

Test-Example -Name "OCP - Simple Demo" `
    -CompilePath "SimpleOcpDemo.java" `
    -RunClass "SimpleOcpDemo"

Set-Location ".."

# Test Full Examples with Packages
Write-Host "`n`n### FULL EXAMPLES (With Packages) ###" -ForegroundColor Magenta

# SRP
Write-Host "`n--- Single Responsibility Principle ---" -ForegroundColor Cyan

Test-Example -Name "SRP - Before (Violation)" `
    -CompilePath "solid/single_responsibility/before" `
    -RunClass "solid.single_responsibility.before.EmployeeDemo"

Test-Example -Name "SRP - After (Compliant)" `
    -CompilePath "solid/single_responsibility/after" `
    -RunClass "solid.single_responsibility.after.EmployeeDemo"

# OCP
Write-Host "`n--- Open/Closed Principle ---" -ForegroundColor Cyan

Test-Example -Name "OCP - After (Compliant)" `
    -CompilePath "solid/open_closed/after" `
    -RunClass "solid.open_closed.after.ShapeDemo"

# LSP
Write-Host "`n--- Liskov Substitution Principle ---" -ForegroundColor Cyan

Test-Example -Name "LSP - Before (Violation)" `
    -CompilePath "solid/liskov_substitution/before" `
    -RunClass "solid.liskov_substitution.before.LspDemo"

Test-Example -Name "LSP - After (Compliant)" `
    -CompilePath "solid/liskov_substitution/after" `
    -RunClass "solid.liskov_substitution.after.LspDemo"

# ISP
Write-Host "`n--- Interface Segregation Principle ---" -ForegroundColor Cyan

Test-Example -Name "ISP - After (Compliant)" `
    -CompilePath "solid/interface_segregation/after" `
    -RunClass "solid.interface_segregation.after.WorkerDemo"

# DIP
Write-Host "`n--- Dependency Inversion Principle ---" -ForegroundColor Cyan

Test-Example -Name "DIP - After (Compliant)" `
    -CompilePath "solid/dependency_inversion/after" `
    -RunClass "solid.dependency_inversion.after.UserServiceDemo"

# Summary
Write-Host "`n`n========================================" -ForegroundColor Cyan
Write-Host "           TEST SUMMARY                  " -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Total Tests:  $totalTests" -ForegroundColor White
Write-Host "Passed:       $passedTests" -ForegroundColor Green
Write-Host "Failed:       $failedTests" -ForegroundColor Red

if ($failedTests -eq 0) {
    Write-Host "`n🎉 All tests passed!" -ForegroundColor Green
} else {
    Write-Host "`n⚠️  Some tests failed. Review output above." -ForegroundColor Yellow
}

Write-Host "========================================" -ForegroundColor Cyan

# Cleanup - remove class files (optional)
$cleanup = Read-Host "`nClean up .class files? (y/n)"
if ($cleanup -eq "y") {
    Write-Host "Cleaning up..." -ForegroundColor Gray
    Get-ChildItem -Path solid -Filter *.class -Recurse | Remove-Item -Force
    Write-Host "✅ Cleanup complete" -ForegroundColor Green
}

