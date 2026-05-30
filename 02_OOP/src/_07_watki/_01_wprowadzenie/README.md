# _01_wprowadzenie - Programowanie wielowatkowe: po co?

## Cel
Ten podrozdzial wprowadza pojecie watku, historyczna motywacje i alternatywy dla "surowych" watkow (`Thread`).

## Kontekst i geneza
- Programy sekwencyjne slabo wykorzystuja wielordzeniowe CPU.
- Wczesniej rownoleglosc budowano glownie na procesach (duza izolacja, wiekszy koszt przelaczania).
- W watkach zyskujemy tansze przelaczanie i wspoldzielona pamiec, ale pojawia sie ryzyko race condition.
- W nowoczesnej Javie oprocz `Thread` czesto wybieramy `ExecutorService` i (Java 21) watki wirtualne.

## Kod i co demonstruje
Plik: `code/ThreadingIntroDemo.java`

Najwazniejsze sekcje:
- `part1_SequentialVsParallel()` - porownanie czasu sekwencyjnie vs rownolegle.
- `part2_SimpleThread()` - minimalny watek na bazie lambdy (`Runnable`).
- `part3_Alternatives()` - `ExecutorService`, `Callable/Future`, `newVirtualThreadPerTaskExecutor()`.

Przykladowy fragment:
```java
Thread t1 = new Thread(() -> results[0] = heavyWork(0, N / 2));
Thread t2 = new Thread(() -> results[1] = heavyWork(N / 2, N));
t1.start();
t2.start();
t1.join();
t2.join();
```

## Diagram
![Wprowadzenie do watkow](diagrams/threading_intro.png)

Zrodlo diagramu: `diagrams/threading_intro.puml`

## Uruchomienie
```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src
javac -d _07_watki/out _07_watki/_01_wprowadzenie/code/ThreadingIntroDemo.java
java -cp _07_watki/out _07_watki._01_wprowadzenie.code.ThreadingIntroDemo
```

## Literatura
- https://docs.oracle.com/javase/tutorial/essential/concurrency/
- https://openjdk.org/jeps/444
- Goetz et al., Java Concurrency in Practice

