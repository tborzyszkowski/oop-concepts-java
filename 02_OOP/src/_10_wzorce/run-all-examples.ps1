# PowerShell - uruchamia wszystkie przyklady z modulu _10_wzorce
$ErrorActionPreference = "Continue"
$src = Split-Path -Parent $MyInvocation.MyCommand.Path
$root = Join-Path $src "out"

Write-Host "=== Kompilacja modulu _10_wzorce ===" -ForegroundColor Cyan
New-Item -ItemType Directory -Force -Path $root | Out-Null

$files = @(
    "_01_wprowadzenie/code/PatternsIntroDemo.java",
    "_02_singleton/code/SingletonDemo.java",
    "_03_factory/code/FactoryDemo.java",
    "_04_builder/code/BuilderDemo.java",
    "_05_adapter/code/AdapterDemo.java",
    "_06_decorator/code/DecoratorDemo.java",
    "_07_facade/code/FacadeDemo.java",
    "_08_proxy/code/ProxyDemo.java",
    "_09_observer/code/ObserverDemo.java",
    "_10_strategy/code/StrategyDemo.java",
    "_11_template_method/code/TemplateMethodDemo.java",
    "_12_command/code/CommandDemo.java",
    "_13_composite/code/CompositeDemo.java"
)

foreach ($f in $files) {
    $full = Join-Path $src $f
    Write-Host "  Kompilacja: $f" -ForegroundColor Yellow
    javac -d $root $full
    if ($LASTEXITCODE -ne 0) {
        Write-Host "  BLAD kompilacji: $f" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "=== Uruchamianie przykladow ===" -ForegroundColor Cyan

$classes = @(
    "_10_wzorce._01_wprowadzenie.code.PatternsIntroDemo",
    "_10_wzorce._02_singleton.code.SingletonDemo",
    "_10_wzorce._03_factory.code.FactoryDemo",
    "_10_wzorce._04_builder.code.BuilderDemo",
    "_10_wzorce._05_adapter.code.AdapterDemo",
    "_10_wzorce._06_decorator.code.DecoratorDemo",
    "_10_wzorce._07_facade.code.FacadeDemo",
    "_10_wzorce._08_proxy.code.ProxyDemo",
    "_10_wzorce._09_observer.code.ObserverDemo",
    "_10_wzorce._10_strategy.code.StrategyDemo",
    "_10_wzorce._11_template_method.code.TemplateMethodDemo",
    "_10_wzorce._12_command.code.CommandDemo",
    "_10_wzorce._13_composite.code.CompositeDemo"
)

foreach ($cls in $classes) {
    Write-Host ""
    Write-Host "--- $cls ---" -ForegroundColor Green
    java -cp $root $cls
}

Write-Host ""
Write-Host "=== Gotowe ===" -ForegroundColor Cyan

