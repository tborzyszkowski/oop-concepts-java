# 10 — Zadania do samodzielnego rozwiązania

## Wymagania wstępne

- Znajomość modułów 01–09 niniejszego rozdziału
- Java 21+, brak zewnętrznych zależności

---

## Zadanie 1 — Bezpieczne parsowanie ⭐

Napisz metodę `OptionalInt safeParseInt(String s)`, która:
- Zwraca `OptionalInt.of(n)` gdy `s` jest poprawną liczbą całkowitą (z możliwymi białymi spacjami)
- Zwraca `OptionalInt.empty()` gdy `s` jest `null`, pusta lub nie jest liczbą całkowitą
- Nie rzuca żadnego wyjątku do wywołującego

Przetestuj dla: `"42"`, `" -7 "`, `"abc"`, `null`, `"3.14"`, `"2147483647"`.

```java
OptionalInt safeParseInt(String s) {
    // TODO
}
```

---

## Zadanie 2 — BoundedStack z wyjątkami ⭐⭐

Zaimplementuj stos o ograniczonej pojemności:

```java
class BoundedStack<T> {
    BoundedStack(int capacity)      // throws IllegalArgumentException jeśli capacity <= 0
    void push(T item)               // throws IllegalStateException jeśli pełny
                                    // throws NullPointerException jeśli item == null
    T pop()                         // throws NoSuchElementException jeśli pusty
    T peek()                        // throws NoSuchElementException jeśli pusty
    int size()
    boolean isEmpty()
}
```

Napisz kod demonstrujący każdy wyjątek.

---

## Zadanie 3 — Własny wyjątek walidacyjny ⭐⭐

Utwórz klasę `PasswordException extends RuntimeException` z:
- Polem `List<String> violations`
- Komunikatem zawierającym wszystkie naruszenia

Napisz metodę `validatePassword(String password)`, która sprawdza:
- Minimum 8 znaków
- Co najmniej jedna duża litera
- Co najmniej jedna cyfra
- Co najmniej jeden znak specjalny z zestawu `!@#$%`

Metoda powinna rzucić `PasswordException` zebrawszy **wszystkie** naruszenia naraz (nie pierwsze napotkanym).

---

## Zadanie 4 — Try-with-resources ⭐⭐

Utwórz klasę `FakeConnection implements AutoCloseable`:
- Konstruktor: `FakeConnection(String url) throws IOException` — rzuca jeśli url pusty
- Metoda: `String query(String sql) throws IOException` — rzuca jeśli sql zawiera `"ERROR"`
- Metoda `close()` — wypisuje na konsolę informację o zamknięciu

Napisz kod, który:
1. Otwiera połączenie i wykonuje poprawne zapytanie
2. Otwiera połączenie i wykonuje błędne zapytanie — weryfikuje że `close()` zawsze się wywołuje
3. Próbuje otworzyć połączenie z pustym URL

---

## Zadanie 5 — Propagacja przez warstwy ⭐⭐⭐

Utwórz klasę `DataProcessingException extends Exception` z polem `cause`.

Napisz trzy metody:
- `int parseAndDouble(String s) throws DataProcessingException` — parsuje i podwaja
- `List<Integer> processAll(List<String> items) throws DataProcessingException` — przetwarza listę, rzuca przy pierwszym błędzie
- Wywołaj obie w `main()` dla listy poprawnej i listy z błędem

Sprawdź że `DataProcessingException.getCause()` wskazuje na `NumberFormatException`.

---

## Zadanie 6 — Własny zasób z try-with-resources ⭐⭐⭐

Utwórz klasę `TimedResource implements AutoCloseable`:
- Czas otwarcia zapisuje w konstruktorze (`System.nanoTime()`)
- Metoda `use(String task)` symuluje pracę (może rzucić `IllegalStateException`)
- Metoda `close()` wypisuje czas życia zasobu w milisekundach

Napisz demo sprawdzające, że czas jest wypisany nawet gdy `use()` rzuci wyjątek.

---

## Zadanie 7 — Walidacja formularza rejestracji ⭐⭐⭐

Utwórz hierarchię:
```
AppException (unchecked)
├── ValidationException  (List<String> violations)
└── DuplicateUserException (String username)
```

Zaimplementuj `UserService` z metodą `register(String username, String email, String password)`:
- Waliduje: username 3–20 znaków, email zawiera `@`, hasło spełnia kryteria z Zadania 3
- Sprawdza duplikaty (prosta kolekcja `Set` w pamięci)
- Dla nowego poprawnego użytkownika — dodaje do kolekcji i zwraca "ID użytkownika"

Przetestuj rejestrację tego samego użytkownika dwa razy.

---

## Rozwiązania

📄 [`solutions/ExceptionExercisesSolutions.java`](solutions/ExceptionExercisesSolutions.java) — zawiera rozwiązania zadań 1–5.

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src
javac -d out _06_wyjatki/_10_zadania/solutions/ExceptionExercisesSolutions.java
java  -cp out _06_wyjatki._10_zadania.solutions.ExceptionExercisesSolutions
```

