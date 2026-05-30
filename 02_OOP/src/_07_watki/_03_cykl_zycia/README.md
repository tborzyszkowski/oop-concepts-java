# 03 — Cykl życia wątku (Thread Lifecycle)

## Cel modułu

Zrozumienie wszystkich stanów `Thread.State` i warunków przejść między nimi. Umiejętność diagnozowania w jakim stanie jest wątek i dlaczego.

---

## 1. Diagram stanów wątku

![Cykl życia wątku](diagrams/thread_lifecycle.png)

---

## 2. Sześć stanów wątku

| Stan | Opis | Jak wejść | Jak wyjść |
|------|------|-----------|-----------|
| `NEW` | Wątek stworzony, ale nie uruchomiony | `new Thread(...)` | `thread.start()` |
| `RUNNABLE` | Wątek ma przydzielony CPU lub czeka na przydział | `start()` | patrz poniżej |
| `BLOCKED` | Czeka na wejście do bloku `synchronized` | zajęty monitor | monitor zwolniony |
| `WAITING` | Czeka na sygnał bezterminowo | `wait()`, `join()`, `park()` | `notify()`, join zakończony |
| `TIMED_WAITING` | Czeka z limitem czasowym | `sleep(ms)`, `wait(ms)`, `join(ms)` | upłynął czas, `interrupt()` |
| `TERMINATED` | `run()` zakończone lub wyjątek | koniec `run()` | — |

```java
// Sprawdzanie stanu wątku
Thread t = new Thread(() -> { Thread.sleep(1000); });
System.out.println(t.getState());   // NEW
t.start();
System.out.println(t.getState());   // RUNNABLE lub TIMED_WAITING
t.join();
System.out.println(t.getState());   // TERMINATED
```

---

## 3. Szczegóły przejść

### NEW → RUNNABLE

```java
Thread t = new Thread(() -> doWork());   // NEW
t.start();                               // RUNNABLE — teraz JVM/OS zarządza przydziałem CPU
```

Wywołanie `t.run()` zamiast `t.start()` **NIE** tworzy nowego wątku — uruchamia metodę `run()` w bieżącym wątku!

---

### RUNNABLE → BLOCKED

```java
Object lock = new Object();

Thread t1 = new Thread(() -> {
    synchronized (lock) {     // T1 wchodzi do synchronized
        Thread.sleep(500);    // trzyma lock przez 500ms
    }
});

Thread t2 = new Thread(() -> {
    synchronized (lock) {     // T2 próbuje wejść → BLOCKED (lock zajęty przez T1)
        System.out.println("T2 w sekcji");
    }
});

t1.start();
Thread.sleep(50);             // dajemy T1 czas na wejście
t2.start();
Thread.sleep(10);
System.out.println(t2.getState());   // BLOCKED
```

**Ważne:** `BLOCKED` **nie** jest przerywalne przez `interrupt()`. Wątek musi czekać aż lock zostanie zwolniony.

---

### RUNNABLE → WAITING / TIMED_WAITING

```java
Object monitor = new Object();
Thread waiter = new Thread(() -> {
    synchronized (monitor) {
        monitor.wait();          // → WAITING (bez timeout)
    }
});

Thread sleeper = new Thread(() -> {
    Thread.sleep(1000);          // → TIMED_WAITING
});

Thread joiner = new Thread(() -> {
    t1.join();                   // → WAITING (czeka na t1)
});
```

`WAITING` vs `TIMED_WAITING`: jedyna różnica to obecność timeoutu. Oba stany zwalniają CPU.

---

### RUNNABLE → TERMINATED

```java
Thread t = new Thread(() -> {
    try {
        doWork();                          // normalne zakończenie
    } catch (RuntimeException e) {
        System.err.println("Wyjątek!");   // wyjątek → też TERMINATED
    }
});

// TERMINATED — nie można ponownie uruchomić!
t.join();
// t.start();  // ← IllegalThreadStateException!
```

---

## 4. Monitorowanie stanów

```java
// Wypisanie stanu wszystkich wątków JVM
ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
long[] ids = tmx.getAllThreadIds();
ThreadInfo[] infos = tmx.getThreadInfo(ids);
for (ThreadInfo info : infos) {
    System.out.printf("%-30s %s%n", info.getThreadName(), info.getThreadState());
}
```

---

## 5. Kod demonstracyjny

📄 [`code/ThreadLifecycleDemo.java`](code/ThreadLifecycleDemo.java)

Sekcje:
- `part1_BasicLifecycle()` — NEW → RUNNABLE → TERMINATED
- `part2_TimedWaiting()` — `sleep()` i `interrupt()`
- `part3_Blocked()` — wejście do `synchronized` gdy lock zajęty
- `part4_Waiting()` — `wait()/notify()`
- `part5_Summary()` — mapowanie stanów na znaczenie praktyczne

### Uruchomienie

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src
javac -d _07_watki/out _07_watki/_03_cykl_zycia/code/ThreadLifecycleDemo.java
java  -cp _07_watki/out _07_watki._03_cykl_zycia.code.ThreadLifecycleDemo
```

---

## 6. Pytania kontrolne

1. W którym stanie wątek faktycznie zużywa czas CPU?
2. Jaka jest różnica między `BLOCKED` a `WAITING`? Który jest przerywany przez `interrupt()`?
3. Co się stanie po wywołaniu `t.start()` na wątku w stanie `TERMINATED`?
4. Dlaczego wątek w stanie `RUNNABLE` może nie wykonywać kodu?

---

## 📚 Literatura i źródła

- [Oracle API — Thread.State](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.State.html)
- [Oracle Tutorial — Thread Lifecycle](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- Brian Goetz et al., *Java Concurrency in Practice*, rozdział 1 i 7

