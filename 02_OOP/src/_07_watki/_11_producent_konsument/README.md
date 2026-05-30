# _11_producent_konsument - Producent i konsument

## Cel
Pokazac klasyczny problem synchronizacji i role komunikacji miedzy watkami na przykladzie bufora jednoelementowego.

## Problem
- Producent dostarcza dane.
- Konsument odbiera dane.
- Bez koordynacji mozemy tracic elementy lub konsumowac ten sam element wielokrotnie.

## Kod
Plik: `code/ProducerConsumerDemo.java`

Scenariusze:
- `part1_WithoutWaitNotify()` - wersja bledna (tylko `synchronized`).
- `part2_WithWaitNotify()` - wersja poprawna (`while + wait + notify`).
- `part3_TwoConsumers()` - dwa konsumery, potrzeba `notifyAll()`.

Fragment:
```java
synchronized (monitor) {
    while (has[0]) monitor.wait();
    buf[0] = i;
    has[0] = true;
    monitor.notify();
}
```

## Diagram
![Producer Consumer](diagrams/producer_consumer.png)

Zrodlo: `diagrams/producer_consumer.puml`

## Uruchomienie
```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src
javac -d _07_watki/out _07_watki/_11_producent_konsument/code/ProducerConsumerDemo.java
java -cp _07_watki/out _07_watki._11_producent_konsument.code.ProducerConsumerDemo
```

## Literatura
- https://en.wikipedia.org/wiki/Producer%E2%80%93consumer_problem
- https://docs.oracle.com/javase/tutorial/essential/concurrency/

