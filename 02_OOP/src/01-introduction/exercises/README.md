# Zadania dla Studentów — Wprowadzenie do OOP

## Wymagania

- Java 21+
- JUnit 5 (`junit.jar` — patrz [`../tdd/README.md`](../tdd/README.md))

## Zadania

| # | Plik | Poziom | Temat | Bazuje na |
|---|------|--------|-------|-----------|
| 1 | [`tasks/RectangleTask.java`](tasks/RectangleTask.java) | ⭐ Podstawowy | Klasa z enkapsulacją, static, walidacja | `classes/`, `fields_and_methods/` |
| 2 | [`tasks/TeamCopyTask.java`](tasks/TeamCopyTask.java) | ⭐⭐ Średni | Głęboka kopia, konstruktor kopiujący | `object_lifecycle/copies/` |
| 3 | [`tasks/PrototypeConfigTask.java`](tasks/PrototypeConfigTask.java) | ⭐⭐⭐ Trudny | Wzorzec Prototype, interfejsy | `object_lifecycle/copies/` |
| 4 | [`tasks/CalcTask.java`](tasks/CalcTask.java) | ⭐⭐ Średni | TDD — napisz testy PRZED implementacją | `tdd/` |

---

## Zadanie 1 — Rectangle ⭐

**Plik:** `tasks/RectangleTask.java`

Zaimplementuj klasę `Rectangle` z:
- enkapsulacją (pola prywatne, gettery, settery z walidacją)
- metodami: `area()`, `perimeter()`, `isSquare()`, `scale(double)`
- polem statycznym `instanceCount`

**Oczekiwane zachowanie:**

```java
Rectangle r = new Rectangle(4.0, 3.0);
r.area();       // 12.0
r.perimeter();  // 14.0
r.isSquare();   // false

Rectangle r2 = new Rectangle(5.0, 5.0);
r2.isSquare();  // true

Rectangle r3 = r.scale(2.0);
r3.getWidth();  // 8.0  — nowy obiekt, r niezmieniony

Rectangle.instanceCount; // 3
```

**Kluczowe aspekty:**
- `scale()` musi zwracać **nowy obiekt**, a nie modyfikować istniejący
- Konstruktor rzuca `IllegalArgumentException` dla `width <= 0` lub `height <= 0`
- `isSquare()` — uwaga na porównanie `double`! Użyj `Double.compare()` lub `Math.abs(a-b) < 1e-9`

**Weryfikacja:** napisz testy JUnit w osobnym pliku `RectangleTest.java` sprawdzające:
- konstruktor z wartościami granicznymi
- walidację setterów
- `scale()` — niezależność obiektu
- `instanceCount` po wielu tworzeniach

---

## Zadanie 2 — Team Deep Copy ⭐⭐

**Plik:** `tasks/TeamCopyTask.java`

Zaimplementuj `TeamMember` i `Team` z **głęboką kopią**:

```java
Team original = new Team("Alpha");
original.addMember(new TeamMember("Anna", "Dev",
    new ArrayList<>(List.of("Java", "SQL"))));

Team copy = original.deepCopy();
copy.getMembers().get(0).addSkill("Python");

// original.getMembers().get(0).getSkills() = ["Java", "SQL"]  ← niezmienione!
// copy.getMembers().get(0).getSkills()     = ["Java", "SQL", "Python"]
```

**Kluczowe aspekty:**
- `TeamMember` musi mieć konstruktor kopiujący który kopiuje **listę skills**
- `Team.deepCopy()` musi tworzyć nowe obiekty `TeamMember` dla każdego członka
- Zmiana nazwy lub roli przez kopię nie może wpłynąć na oryginał
- Zmiana listy `members` w kopii nie może wpłynąć na oryginał

**Typowy błąd:**
```java
// ZLE — shallow copy listy skills
public TeamMember(TeamMember other) {
    this.skills = other.skills; // wspoldzielona lista!
}
// DOBRZE
public TeamMember(TeamMember other) {
    this.skills = new ArrayList<>(other.skills);
}
```

---

## Zadanie 3 — Prototype Config ⭐⭐⭐

**Plik:** `tasks/PrototypeConfigTask.java`

Zaimplementuj system konfiguracji serwera (wzorzec Prototype):

```java
ConfigRegistry reg = new ConfigRegistry();
reg.register("web", new WebServerConfig("web-proto", "localhost", 8080));

// Szybkie klonowanie bez kosztownej inicjalizacji
ServerConfig s1 = reg.spawn("web").withPort(8081).withName("web-1");
ServerConfig s2 = reg.spawn("web").withPort(8082).withName("web-2");

s1.start(); // Starting web server web-1 at localhost:8081
s2.start(); // Starting web server web-2 at localhost:8082
// Prototyp NIEZMIENIONY — port nadal 8080
```

**Dodatkowe wyzwanie:** Zaimplementuj `WebServerConfig.withSsl(boolean)` i
`DatabaseConfig.withPoolSize(int)` — metody fluent zwracające `this`.

---

## Zadanie 4 — TDD Calculator ⭐⭐

**Plik:** `tasks/CalcTask.java`

**Zasada:** Napisz NAJPIERW testy, POTEM implementację.

**Kolejność cykli TDD:**

```
Cykl 1 (RED): test add(2,3)=5  → (GREEN): implementuj add  → (REFACTOR)
Cykl 2 (RED): test subtract    → (GREEN): implementuj subtract ...
Cykl 3 (RED): test multiply    ...
Cykl 4 (RED): test divide(10,0) rzuca wyjątek → (GREEN): walidacja ...
Cykl 5 (RED): test power(2,-1)=0.5 ...
Cykl 6 (RED): test sqrt(-1) rzuca wyjątek ...
Cykl 7 (RED): test history rejestruje operację ...
```

**Minimalny zestaw testów (napisz w `CalcTest.java`):**

```java
@Test void add_TwoPositive_ReturnsSum()
@Test void add_WithNegative_ReturnsCorrect()
@Test void divide_ByZero_ThrowsArithmeticException()
@Test void power_NegativeExponent_ReturnsCorrect()
@Test void sqrt_Negative_ThrowsIllegalArgumentException()
@Test void history_RecordsOperations()
@Test void clearHistory_EmptiesHistory()
```

---

## Rozwiązania

Rozwiązania dostępne po terminie oddania zadań:
[`solutions/Solutions.java`](solutions/Solutions.java)

---

## Wskazówki ogólne

1. **Enkapsulacja** — zawsze pola `private`, dostęp przez metody
2. **Walidacja** — sprawdzaj argumenty w konstruktorze i setterach
3. **Deep copy** — dla każdego mutowalnego pola (List, Map, tablice) utwórz nową kopię
4. **TDD** — nie pisz kodu bez czerwonego testu
5. **assertEquals dla double** — zawsze podaj deltę: `assertEquals(3.14, result, 1e-9)`

