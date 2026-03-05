# Inicjalizacja i Usuwanie Obiektów — Konstruktory i Garbage Collector

## Spis treści

1. [Konstruktory](#1-konstruktory)
2. [Przeciążanie konstruktorów](#2-przeciążanie-konstruktorów)
3. [Delegowanie konstruktorów — `this(...)`](#3-delegowanie-konstruktorów--this)
4. [Bloki inicjalizacyjne](#4-bloki-inicjalizacyjne)
5. [Kolejność inicjalizacji](#5-kolejność-inicjalizacji)
6. [Garbage Collector — automatyczne usuwanie obiektów](#6-garbage-collector--automatyczne-usuwanie-obiektów)
7. [Rodzaje referencji](#7-rodzaje-referencji)
8. [AutoCloseable i try-with-resources](#8-autocloseable-i-try-with-resources)
9. [Uruchamianie przykładów](#9-uruchamianie-przykładów)

---

## 1. Konstruktory

**Konstruktor** to specjalna metoda wywoływana automatycznie przez `new`. Służy do **inicjalizacji obiektu**.

Cechy konstruktora:
- Nazwa identyczna z nazwą klasy
- Brak typu zwracanego (nawet `void`)
- Może mieć dowolną liczbę parametrów
- Może być przeciążony (wiele konstruktorów)

```java
public class Person {
    private String firstName;
    private String lastName;
    private int age;

    // Konstruktor domyślny
    public Person() {
        this.firstName = "Nieznany";
        this.lastName  = "Nieznany";
        this.age = 0;
    }

    // Konstruktor z parametrami
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.age       = age;
    }
}
```

```java
Person p1 = new Person();                  // konstruktor domyślny
Person p2 = new Person("Anna", "Nowak", 30); // konstruktor pełny
```

> 📄 Pełny kod: [`basic/Person.java`](basic/Person.java)

---

## 2. Przeciążanie konstruktorów

Java pozwala na wiele konstruktorów różniących się listą parametrów (analogia do przeciążania metod):

```java
public Person() { ... }
public Person(String firstName, String lastName) { ... }
public Person(String firstName, String lastName, int age) { ... }
public Person(Person other) { ... }  // konstruktor kopiujący
```

**Konstruktor kopiujący** — tworzy nowy obiekt jako kopię istniejącego:

```java
public Person(Person other) {
    this.firstName = other.firstName;
    this.lastName  = other.lastName;
    this.age       = other.age;
    this.email     = other.email;
}
```

```java
Person original = new Person("Jan", "Kowalski", 40);
Person kopia = new Person(original); // niezależna kopia
kopia.setAge(25);
System.out.println(original.getAge()); // 40 — niezmienione
```

---

## 3. Delegowanie konstruktorów — `this(...)`

Jeden konstruktor może wywołać inny za pomocą `this(...)`. Musi być **pierwszą instrukcją**.

```java
// Konstruktor 2-arg deleguje do 3-arg
public Person(String firstName, String lastName) {
    this(firstName, lastName, 0); // ← musi być PIERWSZY!
}

// Konstruktor docelowy (pełny)
public Person(String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName  = lastName;
    this.age       = age;
}
```

### Diagram konstruktorów

![constructors](diagrams/constructors.png)

> 📄 Diagram PlantUML: [`diagrams/constructors.puml`](diagrams/constructors.puml)

---

## 4. Bloki inicjalizacyjne

### Blok inicjalizacyjny instancji

Wykonywany **przy każdym tworzeniu obiektu**, przed konstruktorem:

```java
public class Person {
    private String email;

    // Blok inicjalizacyjny — przed każdym konstruktorem
    {
        System.out.println("Blok inicjalizacyjny");
        email = "brak@email.com"; // wspólna inicjalizacja dla wszystkich konstruktorów
    }

    public Person() { System.out.println("Konstruktor domyślny"); }
    public Person(String name, ...) { System.out.println("Konstruktor pełny"); }
}
```

### Blok statyczny

Wykonywany **raz** — gdy klasa jest ładowana przez JVM:

```java
public class Person {
    static {
        System.out.println("Blok STATYCZNY — raz dla klasy");
        // Inicjalizacja zasobów statycznych, konfiguracja, logowanie
    }
}
```

---

## 5. Kolejność inicjalizacji

```
Pierwsze użycie klasy:
  1. Blok statyczny (tylko RAZ)

Przy każdym new:
  2. Blok inicjalizacyjny instancji
  3. Konstruktor
```

Przykład — uruchomienie `new Person()` po raz pierwszy:

```
[STATIC BLOCK] Klasa Person załadowana przez JVM     ← raz
[INIT BLOCK]   Blok inicjalizacyjny — przed konstruktorem
[CONSTRUCTOR]  Person(firstName, lastName, age) — docelowy
[CONSTRUCTOR]  Person() — domyślny
```

Diagram cyklu życia obiektu:

![object_lifecycle](diagrams/object_lifecycle.png)

> 📄 Diagram PlantUML: [`diagrams/object_lifecycle.puml`](diagrams/object_lifecycle.puml)

---

## 6. Garbage Collector — automatyczne usuwanie obiektów

Java zarządza pamięcią **automatycznie** przez mechanizm Garbage Collector (GC).

### Zasada osiągalności

Obiekt jest **osiągalny** (ang. *reachable*) jeśli istnieje do niego łańcuch silnych referencji z korzenia (np. ze stosu, pól statycznych).

Gdy obiekt jest **nieosiągalny** — GC może go usunąć.

```java
Person p = new Person("Anna", "Nowak", 30);
// Obiekt jest osiągalny przez zmienną p

p = null;
// Brak referencji → obiekt kandydatem do GC
// System.gc() — sugestia, nie gwarancja!
```

### Generacyjny GC — model pamięci

![gc_generations](diagrams/gc_generations.png)

> 📄 Diagram PlantUML: [`diagrams/gc_generations.puml`](diagrams/gc_generations.puml)

| Obszar | Opis | GC |
|--------|------|-----|
| **Eden** | Nowe obiekty | Minor GC (szybki) |
| **Survivor S0/S1** | Przeżyłe Minor GC | Minor GC |
| **Old Gen** | Długożyjące obiekty | Major/Full GC (wolny) |
| **Metaspace** | Metadane klas | Full GC |

### Dostępne GC w Java 21

| Kolektor | Uruchomienie | Charakterystyka |
|----------|-------------|-----------------|
| G1GC (domyślny) | `java App` | Balans throughput/latency |
| ZGC | `-XX:+UseZGC` | Ultra-niskie opóźnienia |
| Shenandoah | `-XX:+UseShenandoahGC` | Concurrent, niskie pause |
| Serial | `-XX:+UseSerialGC` | Jednowątkowy, minimalny overhead |

---

## 7. Rodzaje referencji

```java
import java.lang.ref.*;

// Silna (Strong) — standardowe przypisanie, GC NIE usunie
Person strong = new Person("Anna", "Nowak", 30);

// Słaba (Weak) — GC może usunąć w dowolnym momencie
WeakReference<Person> weak = new WeakReference<>(new Person("X","Y",0));
Person obj = weak.get(); // może zwrócić null!

// Miękka (Soft) — GC usuwa tylko gdy brakuje pamięci
SoftReference<Person> soft = new SoftReference<>(new Person("Z","W",0));

// Fantomowa (Phantom) — po finalizacji, przed zwolnieniem pamięci
// Używana do: śledzenia kiedy obiekt jest zbierany przez GC
```

---

## 8. AutoCloseable i try-with-resources

Zamiast przestarzałego `finalize()` (usuniętego w Java 18) używaj `AutoCloseable`:

```java
public class ResourceHolder implements AutoCloseable {

    public ResourceHolder(String name) {
        System.out.println("[OPEN] " + name);
    }

    @Override
    public void close() {
        System.out.println("[CLOSE] zasób zwolniony automatycznie");
    }
}
```

```java
// try-with-resources — close() wywołane automatycznie!
try (ResourceHolder res = new ResourceHolder("Plik")) {
    res.doWork();
} // <- res.close() tutaj, nawet gdy rzucony wyjątek

// Wiele zasobów — zamykane w odwrotnej kolejności
try (ResourceHolder r1 = new ResourceHolder("Socket");
     ResourceHolder r2 = new ResourceHolder("Plik")) {
    r1.doWork();
    r2.doWork();
} // r2.close(), potem r1.close()
```

> 📄 Pełny kod: [`advanced/ResourceHolder.java`](advanced/ResourceHolder.java)
> 📄 Demo GC: [`advanced/GcDemo.java`](advanced/GcDemo.java)

---

## 9. Uruchamianie przykładów

```bash
# Z katalogu 02_OOP/src

# Konstruktory i inicjalizacja
javac -d . introduction/object_lifecycle/basic/*.java
java introduction.object_lifecycle.basic.PersonDemo

# Garbage Collector
javac -d . introduction/object_lifecycle/advanced/GcDemo.java
java introduction.object_lifecycle.advanced.GcDemo

# AutoCloseable / try-with-resources
javac -d . introduction/object_lifecycle/advanced/ResourceHolder.java
java introduction.object_lifecycle.advanced.ResourceHolder
```

```powershell
.\run-lifecycle-examples.ps1
```

---

## Podsumowanie

| Pojęcie | Opis |
|---------|------|
| **Konstruktor** | Specjalna metoda inicjalizująca obiekt, wywołana przez `new` |
| **Przeciążanie konstruktorów** | Wiele konstruktorów z różnymi parametrami |
| **`this(...)`** | Wywołanie innego konstruktora z konstruktora |
| **Blok statyczny** | Wykonywany raz przy ładowaniu klasy |
| **Blok inicjalizacyjny** | Wykonywany przed każdym konstruktorem |
| **GC** | Automatyczne usuwanie nieosiągalnych obiektów |
| **`finalize()`** | Przestarzałe! Usunięte w Java 18 |
| **`AutoCloseable`** | Nowoczesny sposób zwalniania zasobów |
| **`try-with-resources`** | Gwarantuje wywołanie `close()` |

