# Moduł 2.6: Zadania do Samodzielnego Rozwiązania

Jest to zestaw zadań mających na celu utrwalenie wiedzy o interfejsach w Javie. Zadania są stopniowane pod względem trudności (od jednej gwiazdki ⭐ do trzech ⭐⭐⭐).

Rozwiązania można sprawdzać w katalogu `solutions/`. Ale **najpierw spróbuj samodzielnie!**

## ✅ Kryteria sukcesu / Definition of Done

*   [x] Kod kompiluje się bez błędów.
*   [x] Nazwy klas i interfejsów są zgodne z konwencją PascalCase.
*   [x] Uruchomienie programu daje oczekiwany output (opisany w treści).
*   [x] Zastosowano odpowiednie interfejsy z `java.util` (tam gdzie to konieczne).

---

## Zadanie 1: System Płatności (⭐)

Celem zadania jest przećwiczenie podstaw: definiowania interfejsu i jego implementacji w kilku klasach.

**Plik:** `tasks/PaymentTask.java`

**Treść:**
1.  Stwórz interfejs `Payable` z metodą `pay(double amount)` i `getPaymentInfo()`.
2.  Zaimplementuj ten interfejs w 3 klasach:
    *   `CreditCard` (płatność kartą, maskowany numer)
    *   `PayPalPayment` (płatność przez e-mail)
    *   `BankTransfer` (przelew bankowy)
3.  Zastosuj polimorfizm: stwórz listę różnych płatności i w pętli `for-each` wywołaj metodę `pay()` na każdym elemencie.

---

## Zadanie 2: Studenci i Sortowanie (⭐⭐)

Celem zadania jest praca z interfejsem `Comparable<T>` (naturalne sortowanie) oraz `Comparator<T>` (zewnętrzne sortowanie), co jest kluczowe w kolekcjach Javy.

**Plik:** `tasks/SortableTask.java`

**Treść:**
1.  Klasa `Student` musi implementować `Comparable<Student>`.
2.  Naturalny porządek sortowania: **malejąco po średniej ocen (GPA)**. Jeśli średnie są równe, sortuj rosnąco po nazwisku.
3.  Stwórz listę studentów i posortuj ją używając `Collections.sort()`.
4.  Posortuj tę samą listę alfabetycznie (po nazwisku, potem imieniu), używając `Comparator.comparing(...)`.

---

## Zadanie 3: Transformacje Tekstu (⭐⭐)

Celem zadania jest zrozumienie interfejsów funkcyjnych (`@FunctionalInterface`), metod domyślnych (`default`) oraz kompozycji funkcji.

**Plik:** `tasks/PluginTask.java`

**Treść:**
1.  Zdefiniuj interfejs funkcyjny `TextTransformer` z metodą `String transform(String input)`.
2.  Dodaj metodę default `andThen(TextTransformer next)`, która łączy dwa transformatory w łańcuch.
3.  Zaimplementuj 3 transformatory:
    *   `UpperCase` (zamienia na wielkie litery)
    *   `Trim` (usuwa białe znaki z początku i końca)
    *   `Reverse` (odwraca ciąg znaków)
4.  Stwórz pipeline przetwarzania tekstu: najpierw `trim`, potem `upperCase`, na końcu `reverse`.

---

## Zadanie 4: Kalkulator Figur 2.0 (⭐⭐⭐)

Zadanie na "szóstkę". Łączy w sobie wiedzę o **Sealed Interfaces** (Java 17), **Records** (Java 16) oraz **Pattern Matching for switch** (Java 21).

**Plik:** `tasks/ShapeCalculatorTask.java`

**Treść:**
1.  Stwórz interfejs `sealed Shape` zezwalający tylko na konkretne implementacje.
2.  Stwórz rekordy `Circle`, `Rectangle`, `Triangle` implementujące `Shape`.
3.  W klasie kalkulatora, zaimplementuj metody liczące pole i obwód, używając **Switch Expression** z dopasowaniem wzorca (tzw. switch pattern matching).
    *   Nie używaj klauzuli `default` – kompilator powinien wiedzieć, że obsłużyłeś wszystkie warianty `sealed` interfejsu!
4.  (Opcjonalnie) Użyj "guarded patterns" (`case Rectangle r when r.width() == r.height()`) by wykryć kwadrat.

---

## Uruchomienie i weryfikacja

Wszystkie zadania znajdują się w pakiecie `exercises.tasks`.

Gotowe rozwiązania znajdziesz w pliku `exercises/solutions/Solutions.java`. Możesz go uruchomić, aby zobaczyć oczekiwany rezultat:

```powershell
javac -d out exercises/solutions/Solutions.java
java -cp out exercises.solutions.Solutions
```
