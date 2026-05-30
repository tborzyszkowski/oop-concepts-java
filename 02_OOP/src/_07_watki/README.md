# Moduł 07 — Wątki w Javie (Multithreading)

## Przegląd modułu

Moduł obejmuje kompletne wprowadzenie do programowania wielowątkowego w Javie — od podstaw (pojęcie wątku, cykl życia) przez synchronizację i komunikację między wątkami, aż po klasyczne problemy współbieżności (zakleszczenie, filozofowie przy stole).

---

## Struktura modułu

| Katalog | Temat | Diagramy |
|---------|-------|---------|
| [`_01_wprowadzenie/`](_01_wprowadzenie/README.md) | Geneza wielowątkowości, sekwencyjne vs równoległe, prawo Amdahla | `threading_intro.png` |
| [`_02_watek_vs_proces/`](_02_watek_vs_proces/README.md) | Model pamięci, izolacja, koszt tworzenia | `process_vs_thread.png`, `memory_model.png` |
| [`_03_cykl_zycia/`](_03_cykl_zycia/README.md) | Stany Thread.State, przejścia, diagnostyka | `thread_lifecycle.png` |
| [`_04_watek_glowny/`](_04_watek_glowny/README.md) | Wątek `main`, `currentThread()`, demony | `main_thread.png`, `main_thread_hierarchy.png` |
| [`_05_tworzenie_watku/`](_05_tworzenie_watku/README.md) | Thread vs Runnable vs ExecutorService, wątki wirtualne | `thread_creation.png`, `thread_comparison.png` |
| [`_06_join_isalive/`](_06_join_isalive/README.md) | Koordynacja, Fan-Out/Fan-In, timeout | `join_flow.png`, `join_sequence.png` |
| [`_07_priorytety_volatile/`](_07_priorytety_volatile/README.md) | Priorytety (sugestia schedulera), volatile, Java Memory Model | `priority_volatile.png`, `memory_visibility.png` |
| [`_08_synchronizacja/`](_08_synchronizacja/README.md) | Race condition, count++ na poziomie maszynowym, AtomicInteger | `race_condition.png`, `race_condition_sequence.png` |
| [`_09_synchronized/`](_09_synchronized/README.md) | Monitor, warianty synchronized, granularność locków | `synchronized_monitor.png`, `monitor_concept.png` |
| [`_10_wait_notify/`](_10_wait_notify/README.md) | Protokół wait/notify, Entry Set vs Wait Set, spurious wakeup | `wait_notify_flow.png`, `wait_notify_protocol.png` |
| [`_11_producent_konsument/`](_11_producent_konsument/README.md) | Bufor jednoelementowy, BlockingQueue, notifyAll | `producer_consumer.png`, `buffer_states.png` |
| [`_12_zakleszczenie/`](_12_zakleszczenie/README.md) | Warunki Coffmana, wykrywanie, naprawa przez ordering i tryLock | `deadlock.png`, `coffman_conditions.png` |
| [`_13_filozofowie/`](_13_filozofowie/README.md) | 5 filozofów, stół okrągły, asymetria, tryLock, Semaphore | `philosophers_table.png`, `deadlock_scenario.png`, `dining_philosophers.png` |

---

## Wymagania

- Java 21 lub nowsza
- PlantUML (do regeneracji diagramów) — `plantuml.jar` w katalogu głównym

---

## Jak uruchomić przykłady

### Sposób 1 — skrypt PowerShell (wszystkie przykłady)

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\_07_watki
.\run-all-examples.ps1
```

### Sposób 2 — ręcznie (pojedynczy przykład)

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src

# Kompilacja
javac -d _07_watki/out _07_watki/_03_cykl_zycia/code/ThreadLifecycleDemo.java

# Uruchomienie
java -cp _07_watki/out _07_watki._03_cykl_zycia.code.ThreadLifecycleDemo
```

### Sposób 3 — przykład filozofów (najdłuższy)

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src
javac -d _07_watki/out _07_watki/_13_filozofowie/code/DiningPhilosophers.java
java -cp _07_watki/out _07_watki._13_filozofowie.code.DiningPhilosophers
```

---

## Generowanie diagramów PNG

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\_07_watki
.\generate-diagrams.ps1
```

Skrypt generuje 24 diagramy PNG ze wszystkich plików `.puml` w podkatalogach.

---

## Uwagi

- Przykłady z zakleszczeniem (`_12_zakleszczenie`) używają timeoutów aby nie blokować terminala
- Przykład filozofów (`_13_filozofowie`) demonstruje 3 różne strategie eliminacji deadlocku
- Moduł `_07_priorytety_volatile` pokazuje, że `volatile` nie zastępuje `synchronized`
- Kod jest kompatybilny z Java 21 (używa virtual threads w module `_01_wprowadzenie`)

---

## Literatura

- Brian Goetz et al., *Java Concurrency in Practice*, Addison-Wesley, 2006 — **kluczowa lektura**
- [Oracle Java Tutorial — Concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- Doug Lea, *Concurrent Programming in Java*, 2nd ed., Addison-Wesley, 1999
- [JEP 444: Virtual Threads (Java 21)](https://openjdk.org/jeps/444)
- E.W. Dijkstra, *EWD310 — Hierarchical Ordering*, 1971 (filozofowie)
- [Java Language Specification §17 — Threads and Locks](https://docs.oracle.com/javase/specs/jls/se21/html/jls-17.html)

