# PowerShell — uruchamia wszystkie przykłady z modułu _07_watki
$ErrorActionPreference = "Continue"
$src = Split-Path -Parent $MyInvocation.MyCommand.Path
$root = Join-Path $src "out"

Write-Host "=== Kompilacja modułu _07_watki ===" -ForegroundColor Cyan
New-Item -ItemType Directory -Force -Path $root | Out-Null

$files = @(
    "_01_wprowadzenie/code/ThreadingIntroDemo.java",
    "_02_watek_vs_proces/code/ProcessVsThreadDemo.java",
    "_03_cykl_zycia/code/ThreadLifecycleDemo.java",
    "_04_watek_glowny/code/MainThreadDemo.java",
    "_05_tworzenie_watku/code/ThreadCreationDemo.java",
    "_06_join_isalive/code/JoinAliveDemo.java",
    "_07_priorytety_volatile/code/PriorityVolatileDemo.java",
    "_08_synchronizacja/code/SynchronizationDemo.java",
    "_09_synchronized/code/SynchronizedBlockDemo.java",
    "_10_wait_notify/code/WaitNotifyDemo.java",
    "_11_producent_konsument/code/ProducerConsumerDemo.java",
    "_12_zakleszczenie/code/DeadlockDemo.java",
    "_13_filozofowie/code/DiningPhilosophers.java"
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
    "_07_watki._01_wprowadzenie.code.ThreadingIntroDemo",
    "_07_watki._02_watek_vs_proces.code.ProcessVsThreadDemo",
    "_07_watki._03_cykl_zycia.code.ThreadLifecycleDemo",
    "_07_watki._04_watek_glowny.code.MainThreadDemo",
    "_07_watki._05_tworzenie_watku.code.ThreadCreationDemo",
    "_07_watki._06_join_isalive.code.JoinAliveDemo",
    "_07_watki._07_priorytety_volatile.code.PriorityVolatileDemo",
    "_07_watki._08_synchronizacja.code.SynchronizationDemo",
    "_07_watki._09_synchronized.code.SynchronizedBlockDemo",
    "_07_watki._10_wait_notify.code.WaitNotifyDemo",
    "_07_watki._11_producent_konsument.code.ProducerConsumerDemo",
    "_07_watki._12_zakleszczenie.code.DeadlockDemo",
    "_07_watki._13_filozofowie.code.DiningPhilosophers"
)

foreach ($cls in $classes) {
    Write-Host ""
    Write-Host "--- $cls ---" -ForegroundColor Green
    java -cp $root $cls
}

Write-Host ""
Write-Host "=== Gotowe ===" -ForegroundColor Cyan

