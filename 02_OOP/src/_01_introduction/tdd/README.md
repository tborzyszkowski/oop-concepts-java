# TDD — Test-Driven Development w Javie

## Spis treści

1. [Czym jest TDD?](#1-czym-jest-tdd)
2. [Cykl Red–Green–Refactor](#2-cykl-redgreenrefactor)
3. [JUnit 5 — podstawy](#3-junit-5--podstawy)
4. [Adnotacje JUnit 5](#4-adnotacje-junit-5)
5. [Asercje](#5-asercje)
6. [Testy parametryzowane](#6-testy-parametryzowane)
7. [Przykład TDD krok po kroku — Stack](#7-przykład-tdd-krok-po-kroku--stack)
8. [Testy dla istniejącego kodu](#8-testy-dla-istniejącego-kodu)
9. [Uruchamianie testów](#9-uruchamianie-testów)

---

## 1. Czym jest TDD?

**Test-Driven Development** to technika wytwarzania oprogramowania, w której:

1. **Najpierw piszesz test** (który nie przechodzi — bo kodu nie ma)
2. **Potem piszesz minimalną implementację** (test przechodzi)
3. **Refaktoryzujesz** (poprawiasz kod bez zmiany zachowania)

> *"Write the test you wish you had"* — Kent Beck

### Korzyści TDD

| Korzyść | Opis |
|---------|------|
| Kod z definicji testowalny | Projektujesz API pod kątem użycia |
| Dokumentacja żyjąca | Testy opisują oczekiwane zachowanie |
| Bezpieczny refactoring | Testy wychwytują regresje |
| Mniejsze debugowanie | Błędy lokalizowane natychmiast |
| Mniejsza złożoność kodu | Nie piszesz więcej niż potrzeba |

---

## 2. Cykl Red–Green–Refactor

```
    ┌─────────────────────────────┐
    │                             │
    ▼                             │
  RED                            │
  Napisz failing test            │
  (kod nie istnieje)             │
    │                             │
    ▼                             │
  GREEN                          │
  Minimalna implementacja        │
  (test przechodzi)              │
    │                             │
    ▼                             │
  REFACTOR ─────────────────────►┘
  Popraw kod, testy muszą
  nadal przechodzić
```

### Przykład — jeden cykl

**RED** — napisz test:
```java
@Test
void newStack_IsEmpty() {
    Stack<Integer> stack = new Stack<>();
    assertTrue(stack.isEmpty()); // FAIL: Stack nie istnieje
}
```

**GREEN** — minimalna implementacja:
```java
public class Stack<T> {
    private int size = 0;
    public boolean isEmpty() { return size == 0; }
}
```

**REFACTOR** — dodaj tablicę wewnętrzną, zachowaj test:
```java
public class Stack<T> {
    private Object[] elements = new Object[16];
    private int size = 0;
    public boolean isEmpty() { return size == 0; }
}
```

> 📄 Pełna implementacja: [`src/Stack.java`](src/Stack.java)
> 📄 Testy: [`tests/StackTest.java`](tests/StackTest.java)

---

## 3. JUnit 5 — podstawy

JUnit 5 to standard testowania w Javie. Składa się z:
- **JUnit Platform** — silnik uruchamiania testów
- **JUnit Jupiter** — API do pisania testów
- **JUnit Vintage** — kompatybilność z JUnit 4

### Klasa testowa

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Opis zestawu testów")
class MyClassTest {

    private MyClass obj;

    @BeforeEach          // Uruchamiane przed KAŻDYM testem
    void setUp() {
        obj = new MyClass();
    }

    @AfterEach           // Uruchamiane po KAŻDYM tescie
    void tearDown() { }

    @BeforeAll           // Raz przed wszystkimi testami (static!)
    static void initAll() { }

    @Test
    @DisplayName("Opis testu")
    void myTest() {
        // Given
        int x = 5;
        // When
        int result = obj.double(x);
        // Then
        assertEquals(10, result);
    }
}
```

---

## 4. Adnotacje JUnit 5

| Adnotacja | Opis |
|-----------|------|
| `@Test` | Metoda testowa |
| `@DisplayName` | Czytelna nazwa testu |
| `@BeforeEach` | Uruchom przed każdym testem |
| `@AfterEach` | Uruchom po każdym teście |
| `@BeforeAll` | Raz przed wszystkimi (musi być `static`) |
| `@AfterAll` | Raz po wszystkich (musi być `static`) |
| `@Nested` | Zagnieżdżone grupy testów |
| `@Disabled` | Pomija test |
| `@Tag` | Tagowanie testów |
| `@ParameterizedTest` | Test z wieloma zestawami danych |
| `@RepeatedTest(n)` | Powtórz test n razy |
| `@Order(n)` | Kolejność wykonania |

---

## 5. Asercje

```java
// Równość
assertEquals(expected, actual);
assertEquals(3.14, result, 1e-9);   // delta dla double
assertNotEquals(0, result);

// Prawda / Fałsz
assertTrue(condition);
assertFalse(condition);

// Null
assertNull(obj);
assertNotNull(obj);

// Referencje
assertSame(obj1, obj2);      // ten sam obiekt (==)
assertNotSame(obj1, obj2);   // różne obiekty

// Wyjątki
assertThrows(IllegalArgumentException.class,
    () -> counter.setName(null));

assertDoesNotThrow(() -> counter.increment());

// Wiele asercji naraz (wszystkie sprawdzane, nawet jeśli jedna fail)
assertAll(
    () -> assertEquals("Anna",    person.getFirstName()),
    () -> assertEquals("Nowak",   person.getLastName()),
    () -> assertEquals(30,        person.getAge())
);
```

---

## 6. Testy parametryzowane

```java
// Proste wartości
@ParameterizedTest
@ValueSource(ints = {2, 3, 5, 7, 11, 13})
void isPrime(int prime) {
    assertTrue(MathUtils.isPrime(prime));
}

// CSV — wiele kolumn
@ParameterizedTest(name = "factorial({0}) = {1}")
@CsvSource({"0,1", "1,1", "3,6", "5,120"})
void factorial(int n, long expected) {
    assertEquals(expected, MathUtils.factorial(n));
}
```

> 📄 Testy z parametrami: [`../../fields_and_methods/tests/CounterAndMathTest.java`](../../fields_and_methods/tests/CounterAndMathTest.java)

---

## 7. Przykład TDD krok po kroku — Stack

### Cykl 1 — pusty stos

```java
// RED: napisz test
@Test
void newStack_IsEmpty() {
    Stack<Integer> stack = new Stack<>();
    assertTrue(stack.isEmpty()); // FAIL
}

// GREEN: minimalna implementacja
public boolean isEmpty() { return true; }
```

### Cykl 2 — push dodaje element

```java
// RED
@Test
void afterPush_StackIsNotEmpty() {
    stack.push(42);
    assertFalse(stack.isEmpty()); // FAIL - isEmpty() zawsze true
}

// GREEN
private int size = 0;
public void push(T e) { size++; }
public boolean isEmpty() { return size == 0; }
```

### Cykl 3 — pop zwraca element (LIFO)

```java
// RED
@Test
void pop_ReturnsLastPushedElement() {
    stack.push(10);
    stack.push(20);
    assertEquals(20, stack.pop()); // FAIL - brak pop()
}

// GREEN
private Object[] elements = new Object[16];

public void push(T e)  { elements[size++] = e; }

@SuppressWarnings("unchecked")
public T pop() { return (T) elements[--size]; }
```

### Cykl 4 — pop na pustym rzuca wyjątek

```java
// RED
@Test
void pop_OnEmptyStack_ThrowsException() {
    assertThrows(NoSuchElementException.class, () -> stack.pop());
    // FAIL - IndexArrayOutOfBounds zamiast NoSuchElementException
}

// GREEN
public T pop() {
    if (isEmpty()) throw new NoSuchElementException("Stos jest pusty");
    return (T) elements[--size];
}
```

---

## 8. Testy dla istniejącego kodu

Nawet jeśli kod już istnieje, TDD można stosować retrospektywnie:

```java
// PersonTest.java — testy dla istniejącej klasy Person
@Test
void copyConstructorCreatesIndependentCopy() {
    Person original = new Person("Anna", "Kowalska", 30);
    Person copy = new Person(original);

    copy.setAge(25);

    assertEquals(30, original.getAge(), "Oryginal nie powinien sie zmienic");
    assertEquals(25, copy.getAge());
}
```

> 📄 Testy Person: [`../../object_lifecycle/tests/PersonTest.java`](../../object_lifecycle/tests/PersonTest.java)
> 📄 Testy Counter/Math: [`../../fields_and_methods/tests/CounterAndMathTest.java`](../../fields_and_methods/tests/CounterAndMathTest.java)

---

## 9. Uruchamianie testów

### Pobierz JUnit 5 (standalone jar)

```powershell
# Pobierz junit-platform-console-standalone
$url = "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.0/junit-platform-console-standalone-1.11.0.jar"
Invoke-WebRequest -Uri $url -OutFile "junit.jar"
```

### Kompilacja i uruchomienie

```powershell
# Korzeń kompilacji = 02_OOP/src/01-introduction
cd 02_OOP\src\01-introduction

# Kompilacja Stack
javac -cp "out;..\..\..\junit.jar" -d out tdd/src/Stack.java
javac -cp "out;..\..\..\junit.jar" -d out tdd/tests/StackTest.java

# Uruchomienie
java -cp "out;..\..\..\junit.jar" org.junit.platform.console.ConsoleLauncher `
     --select-class=introduction.tdd.tests.StackTest
```

### Skrypt PowerShell

```powershell
.\run-tdd-tests.ps1
```

### Skrócony output

```
[OK]  [Cykl 1] Nowy stos jest pusty
[OK]  [Cykl 1] Nowy stos ma rozmiar 0
[OK]  [Cykl 2] Po push stos nie jest pusty
...
[OK]  [Cykl 5] Stos obsluguje wiele elementow
16 tests passed
```

---

## Dobre praktyki TDD

| Zasada | Opis |
|--------|------|
| **Jeden test naraz** | Pisz jeden failing test, nie całą serię |
| **Minimalna implementacja** | Nie pisz więcej kodu niż potrzeba do GREEN |
| **Given-When-Then** | Struktura ciała testu |
| **Czytelne nazwy** | `method_state_expectedBehavior` |
| **Izolacja** | Każdy test niezależny, `@BeforeEach` do setUp |
| **F.I.R.S.T.** | Fast, Independent, Repeatable, Self-validating, Timely |

