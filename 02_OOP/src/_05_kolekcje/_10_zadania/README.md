# Moduł 5.10: Zadania do samodzielnego rozwiązania

Sekcja utrwala materiał z modułów 5.1–5.9: kolekcje, typy generyczne, Set, Iterator, Map, Comparator i Stream API.

---

## Zadanie 1 ⭐ — Usuń duplikaty zachowując kolejność

**Problem:** Masz listę z duplikatami. Usuń powtórzenia, zachowując oryginalną kolejność pierwszego wystąpienia każdego elementu.

**Przykład:**
```
wejście:  [1, 3, 2, 1, 4, 3, 5]
wyjście:  [1, 3, 2, 4, 5]
```

**Wskazówka:** Istnieje kolekcja, która łączy unikalność `Set` z zachowaniem kolejności wstawiania...

**Do zaimplementowania:**
```java
public static <T> List<T> removeDuplicatesPreserveOrder(List<T> list) {
    // TODO
}
```

**Wskazówki:** patrz moduł 5.2 (LinkedHashSet), moduł 5.3 (generyki).

---

## Zadanie 2 ⭐ — Odwrócona mapa

**Problem:** Dana jest mapa `Map<K, V>`. Odwróć ją — zamień klucze i wartości miejscami.

**Przykład:**
```
wejście:  {a=1, b=2, c=3}
wyjście:  {1=a, 2=b, 3=c}
```

**Uwaga:** Zakładamy, że wartości są unikalne (brak kolizji).

**Do zaimplementowania:**
```java
public static <K, V> Map<V, K> invertMap(Map<K, V> original) {
    // TODO
}
```

**Wskazówki:** patrz moduł 5.6 (forEach na mapie).

---

## Zadanie 3 ⭐⭐ — Najczęstszy element

**Problem:** Znajdź element, który najczęściej występuje w liście.

**Przykład:**
```
wejście:  [kot, pies, kot, ryba, pies, kot]
wyjście:  Optional[kot]  (wystąpił 3 razy)
```

**Do zaimplementowania:**
```java
public static <T> Optional<T> mostFrequent(List<T> list) {
    // TODO
}
```

**Wskazówki:** patrz moduł 5.6 (licznik częstości), moduł 5.8 (groupingBy + counting + max).

---

## Zadanie 4 ⭐⭐ — Partycjonowanie: parzyste/nieparzyste

**Problem:** Podziel listę liczb na dwie grupy: parzyste i nieparzyste.

**Przykład:**
```
wejście:  [1, 2, 3, 4, 5, 6, 7, 8]
wyjście:  {false=[1, 3, 5, 7], true=[2, 4, 6, 8]}
```

**Do zaimplementowania:**
```java
public static Map<Boolean, List<Integer>> partitionByParity(List<Integer> nums) {
    // TODO — hint: Collectors.partitioningBy
}
```

**Wskazówki:** patrz moduł 5.8 (`Collectors.partitioningBy`).

---

## Zadanie 5 ⭐⭐ — K-ty najmniejszy element

**Problem:** Znajdź k-ty (0-based) najmniejszy element dowolnej kolekcji porównywalnych elementów.

**Przykład:**
```
wejście:  [5, 3, 8, 1, 9], k=2
wyjście:  Optional[5]   (kolejność: 1, 3, 5, 8, 9 → indeks 2 = 5)
```

**Do zaimplementowania:**
```java
public static <T extends Comparable<T>> Optional<T> kthSmallest(Collection<T> col, int k) {
    // TODO
}
```

**Wskazówki:** patrz moduł 5.7 (Comparator), moduł 5.8 (sorted + skip + findFirst).

---

## Zadanie 6 ⭐ — Lista słów → mapa słowo/długość

**Problem:** Zamień listę słów na mapę `słowo → długość_słowa`.

**Przykład:**
```
wejście:  [Java, Kolekcje, Stream]
wyjście:  {Java=4, Kolekcje=8, Stream=6}
```

**Do zaimplementowania:**
```java
public static Map<String, Integer> wordsToLengthMap(List<String> words) {
    // TODO — hint: Collectors.toMap
}
```

**Wskazówki:** patrz moduł 5.8 (`Collectors.toMap`).

---

## Zadanie 7 ⭐⭐ — Czy lista jest posortowana?

**Problem:** Sprawdź, czy lista porównywalnych elementów jest posortowana rosnąco.

**Przykład:**
```
[1, 2, 5]   → true
[1, 5, 2]   → false
[]           → true (lista pusta jest posortowana)
```

**Do zaimplementowania:**
```java
public static <T extends Comparable<T>> boolean isSorted(List<T> list) {
    // TODO
}
```

**Wskazówki:** patrz moduł 5.7 (Comparable), moduł 5.1 (iteracja po liście z indeksem).

---

## Zadanie 8 ⭐⭐⭐ — Transpozycja macierzy

**Problem:** Dana jest macierz jako `List<List<T>>`. Transponuj ją (zamień wiersze na kolumny).

**Przykład:**
```
wejście:  [[1, 2, 3],
            [4, 5, 6]]
wyjście:  [[1, 4],
            [2, 5],
            [3, 6]]
```

**Do zaimplementowania:**
```java
public static <T> List<List<T>> transpose(List<List<T>> matrix) {
    // TODO
}
```

**Wskazówki:** patrz moduł 5.1 (ArrayList), moduł 5.3 (generyki).

---

## Zadanie 9 ⭐⭐⭐ — Kompletny system magazynowy (projekt)

**Problem:** Zaimplementuj prosty system zarządzania magazynem:

1. Klasa `WarehouseItem(String name, String category, int quantity, double unitPrice)`.
2. Metoda `totalValue()` obliczająca wartość towaru (quantity × unitPrice).
3. System przechowuje produkty w odpowiedniej kolekcji (pomyśl: które?).
4. Zaimplementuj metody:
   - `addItem(WarehouseItem)` — dodaj lub zaktualizuj ilość jeśli już istnieje,
   - `removeItem(String name)` — usuń produkt,
   - `getTotalWarehouseValue()` — łączna wartość magazynu,
   - `getItemsByCategory()` — `Map<String, List<WarehouseItem>>` pogrupowane wg kategorii,
   - `getTopNByValue(int n)` — N produktów o najwyższej wartości.

**Wskazówki:** patrz moduły 5.2, 5.6, 5.7, 5.8.

---

## Zadanie 10 ⭐⭐⭐ — Histogram słów z pliku

**Problem:** Wczytaj tekst (hardkodowany lub z pliku), podziel go na słowa, zlicz wystąpienia, wypisz 10 najczęstszych słów posortowanych malejąco.

**Szkielet:**
```java
String text = "... duży tekst ...";
String[] words = text.toLowerCase().replaceAll("[^a-ząęóśłżźćń ]", "").split("\\s+");

// TODO: zbuduj mapę częstości i wypisz top 10
```

**Wskazówki:** patrz moduły 5.6 (merge/licznik), 5.8 (sorted + limit + forEach).

---

## Rozwiązania referencyjne

Gotowe implementacje znajdziesz w: [`solutions/CollectionsExercisesSolutions.java`](solutions/CollectionsExercisesSolutions.java)

---

## Uruchomienie rozwiązań

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_05_kolekcje\_10_zadania"
.\run-examples.ps1
```

---

## Wskazówki TDD

1. Zacznij od napisania testu, który opisuje oczekiwane zachowanie (np. `removeDuplicatesPreserveOrder`).
2. Uruchom test — powinien się nie kompilować lub być czerwony.
3. Dodaj minimalną implementację.
4. Uruchom test — powinien być zielony.
5. Zrefaktoryzuj i uruchom ponownie.

---

## 📚 Literatura i materiały dodatkowe

- **Oracle Tutorial — Collections:** <https://docs.oracle.com/javase/tutorial/collections/index.html>
- **Effective Java (3rd ed.)**, Joshua Bloch — Items 45–58 (Streams, Collections)
- **Baeldung — Java Collectors:** <https://www.baeldung.com/java-8-collectors>

