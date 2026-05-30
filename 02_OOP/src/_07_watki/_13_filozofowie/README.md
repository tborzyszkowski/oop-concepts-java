# _13_filozofowie - Problem pieciu filozofow

## Cel
Pokazac zespolowe zakleszczenie i porownac strategie jego unikania na klasycznym problemie Dijkstry.

## Scenariusze
- Wersja bledna: kazdy filozof bierze najpierw lewy widelec -> latwy deadlock.
- Rozwiazanie 1: asymetria (jeden filozof odwraca kolejnosc).
- Rozwiazanie 2: `tryLock()` z wycofaniem i ponowna proba.

## Kod
Plik: `code/DiningPhilosophers.java`

Najwazniejsze elementy:
- `DeadlockTable` - demonstracja zakleszczenia.
- `AsymmetricTable` - rozwiazanie przez zlamanie symetrii.
- `TryLockTable` - rozwiazanie retry/backoff.
- `demonstrateDeadlock()`, `runAsymmetric()`, `runTryLock()`.

Fragment:
```java
if (forks[left].tryLock()) {
    try {
        if (forks[right].tryLock()) {
            try { /* jedzenie */ }
            finally { forks[right].unlock(); }
        }
    } finally { forks[left].unlock(); }
}
```

## Diagram
![Dining philosophers](diagrams/dining_philosophers.png)

Zrodlo: `diagrams/dining_philosophers.puml`

## Uruchomienie
```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src
javac -d _07_watki/out _07_watki/_13_filozofowie/code/DiningPhilosophers.java
java -cp _07_watki/out _07_watki._13_filozofowie.code.DiningPhilosophers
```

## Literatura
- https://en.wikipedia.org/wiki/Dining_philosophers_problem
- https://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html

