# Moduł 2.3: Zaawansowane wykorzystanie Interfejsów

## Wprowadzenie

### 🎯 Czego się nauczysz w tym module?
*   Używać wbudowanych interfejsów Javy: `Comparable`, `Interable`, `Function`.
*   Sortować kolekcje używając `Comparable` (naturalny porządek) i `Comparator` (własny porządek).
*   Korzystać z wyrażeń **Lambda** do szybkiej implementacji interfejsów funkcyjnych.
*   Zrozumieć mechanizm pętli `for-each` i interfejsu `Iterable`.

W tym module przyjrzymy się interfejsom, które nie są częścią naszego kodu dziedzinowego (jak Kaczka czy Samochód), ale są **wbudowane w Javę** (`java.util.*`) i pełnią kluczową rolę w ekosystemie, w tym w sortowaniu kolekcji, pętlach czy programowaniu funkcyjnym.

---

## 1. Porównywanie i `Comparable` vs `Comparator`

Gdy tworzymy listę obiektów, często chcemy ją posortować. Java dostarcza dwa interfejsy do tego celu:

### `Comparable<T>`
Definiuje **naturalny porządek** sortowania, np. "od A do Z" dla napisów czy rosnąco dla liczb. Obiekt "wie" jak porównać się z innym obiektem tego samego typu.

![Comparable](diagrams/comparable_hierarchy.png)

```java
public class Product implements Comparable<Product> {
    private double price;

    @Override
    public int compareTo(Product other) {
        // Naturalne sortowanie po cenie
        return Double.compare(this.price, other.price);
    }
}
```
Użycie: `Collections.sort(products);`

### `Comparator<T>`
Definiuje **zewnętrzny porządek** sortowania. Pozwala stworzyć wiele różnych strategii sortowania dla tej samej klasy (np. raz po cenie, a raz po nazwie).

```java
// Comparator jako lambda
Comparator<Product> byName = Comparator.comparing(Product::getName);
products.sort(byName);
```
Więcej w [ComparableDemo.java](ComparableDemo.java).

---

## 2. Interfejsy Funkcyjne i Lambdy

Od Javy 8, interfejs, który zawiera **dokładnie jedną metodę abstrakcyjną**, nazywamy **interfejsem funkcyjnym**. Dzięki temu kompilator pozwala użyć wyrażenia **Lambda** jako zwięzłej implementacji tego interfejsu.

![Interfejsy Funkcyjne](diagrams/functional_interfaces.png)

### Adnotacja `@FunctionalInterface`
Podpowiada kompilatorowi, że ten interfejs ma być funkcyjny (i generuje błąd w przeciwnym razie).

Java dostarcza gotowe interfejsy funkcyjne w `java.util.function`:
*   `Predicate<T>`: `T -> boolean` (Testowanie warunku)
*   `Function<T, R>`: `T -> R` (Transformacja)
*   `Consumer<T>`: `T -> void` (Konsumpcja, np. wydruk)
*   `Supplier<T>`: `() -> T` (Produkcja, fabryka)

```java
// Lambda w akcji
Predicate<String> isValid = s -> s.length() > 3;
Function<String, String> toUpper = String::toUpperCase; // Method reference
```
Przykłady w [FunctionalDemo.java](FunctionalDemo.java).

---

## 3. Pętle, `Iterable<T>` i `Iterator<T>`

Zastanawialiście się, dlaczego pętla `for-each` działa na tablicach i kolekcjach?

```java
for (String s : list) { ... }
```

Dzieje się tak, ponieważ `Collection` implementuje interfejs `Iterable`.
Interfejs `Iterable<T>` wymusza implementację metody `iterator()`, która zwraca obiekt `Iterator<T>`. To `Iterator` "chodzi" po kolekcji.

![Iterable i Iterator](diagrams/iterable_iterator.png)

Możemy stworzyć własną klasę implementującą `Iterable`, np. zakres liczb [NumberRange.java](NumberRange.java), po którym można iterować w pętli `for`.

```java
public class NumberRange implements Iterable<Integer> {
    // ...
    @Override
    public Iterator<Integer> iterator() { ... }
}

// Użycie:
for (int n : new NumberRange(1, 10)) {
    System.out.println(n);
}
```
Demo w [IterableDemo.java](IterableDemo.java).

---

## ⚠️ Najczęstsze błędy początkujących

1.  **Mylenie `Comparable` z `Comparator`:**
    *   `Comparable` implementujemy *wewnątrz* naszej klasy (np. `Product`).
    *   `Comparator` tworzymy *osobno* (jako lambdę lub klasę), gdy chcemy posortować klasę w inny sposób (lub taką, której nie możemy zmienić).

2.  **Ignorowanie `@FunctionalInterface`:**
    Adnotacja nie jest wymagana, ale *bardzo pomocna*. Chroni przed przypadkowym dodaniem drugiej metody abstrakcyjnej, co zepsułoby wszystkie lambdy w kodzie.

3.  **Modyfikacja kolekcji w pętli `for-each`:**
    Interfejs `Iterator` pozwala na bezpieczne usuwanie (`remove()`), ale pętla `for-each` ukrywa iterator. Próba usunięcia elementu z listy w `for-each` rzuci `ConcurrentModificationException`.

---

## 📚 Literatura i materiały dodatkowe

*   **Cay S. Horstmann**, *Java. Techniki zaawansowane*, Rozdział 1: Strumienie (wprowadzenie do interfejsów funkcyjnych).
*   [Oracle Tutorial: Lambda Expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
*   [Oracle Tutorial: Object Ordering](https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html)

---

## Uruchomienie przykładów

```powershell
.\run-examples.ps1
```
