# Moduł 07 — Wątki w Javie

## Struktura modułu

| Katalog | Temat | Kod | Diagram |
|---------|-------|-----|---------|
| [`_01_wprowadzenie/`](_01_wprowadzenie/README.md) | Wprowadzenie do wątków | `ThreadingIntroDemo.java` | threading_intro.png |
| [`_02_watek_vs_proces/`](_02_watek_vs_proces/README.md) | Wątek vs Proces | `ProcessVsThreadDemo.java` | process_vs_thread.png |
| [`_03_cykl_zycia/`](_03_cykl_zycia/README.md) | Cykl życia wątku | `ThreadLifecycleDemo.java` | thread_lifecycle.png |
| [`_04_watek_glowny/`](_04_watek_glowny/README.md) | Wątek główny | `MainThreadDemo.java` | main_thread.png |
| [`_05_tworzenie_watku/`](_05_tworzenie_watku/README.md) | Tworzenie wątku | `ThreadCreationDemo.java` | thread_creation.png |
| [`_06_join_isalive/`](_06_join_isalive/README.md) | join() i isAlive() | `JoinAliveDemo.java` | join_flow.png |
| [`_07_priorytety_volatile/`](_07_priorytety_volatile/README.md) | Priorytety i volatile | `PriorityVolatileDemo.java` | priority_volatile.png |
| [`_08_synchronizacja/`](_08_synchronizacja/README.md) | Synchronizacja | `SynchronizationDemo.java` | race_condition.png |
| [`_09_synchronized/`](_09_synchronized/README.md) | synchronized block | `SynchronizedBlockDemo.java` | synchronized_monitor.png |
| [`_10_wait_notify/`](_10_wait_notify/README.md) | wait/notify | `WaitNotifyDemo.java` | wait_notify_flow.png |
| [`_11_producent_konsument/`](_11_producent_konsument/README.md) | Producent-Konsument | `ProducerConsumerDemo.java` | producer_consumer.png |
| [`_12_zakleszczenie/`](_12_zakleszczenie/README.md) | Zakleszczenie | `DeadlockDemo.java` | deadlock.png |
| [`_13_filozofowie/`](_13_filozofowie/README.md) | Problem 5 filozofów | `DiningPhilosophers.java` | dining_philosophers.png |

## Wymagania

- Java 21 lub nowsza
- PlantUML (opcjonalnie, do regeneracji diagramów)

## Jak uruchomić przykłady

### Sposób 1 — skrypt PowerShell

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\_07_watki
.\run-all-examples.ps1
```

### Sposób 2 — ręcznie

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src

# Kompilacja
javac -d _07_watki/out _07_watki/_03_cykl_zycia/code/ThreadLifecycleDemo.java

# Uruchomienie
java -cp _07_watki/out _07_watki._03_cykl_zycia.code.ThreadLifecycleDemo
```

## Generowanie diagramów PNG

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\_07_watki
.\generate-diagrams.ps1
```

## Uwagi

- Przykłady z zakleszczeniem (`_12_zakleszczenie`) używają timeoutów aby nie blokować terminala
- Przykład filozofów (`_13_filozofowie`) pokazuje różne **rozwiązania** problemu zakleszczenia
- Kod jest kompatybilny z Java 21 (używa virtual threads w module 01)

## Literatura

- Brian Goetz et al., *Java Concurrency in Practice*, Addison-Wesley, 2006
- [Java Tutorials — Concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- Doug Lea, *Concurrent Programming in Java*, 2nd ed., Addison-Wesley, 1999
- [JEP 444: Virtual Threads](https://openjdk.org/jeps/444)

