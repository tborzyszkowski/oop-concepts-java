# Moduł 5.9: Projekt — Solver Anagramów

## Wprowadzenie

Ten moduł integruje wszystkie wcześniej poznane koncepcje (kolekcje, mapy, iteratory, komparatory, strumienie) w jednym, praktycznym programie.

### 🎯 Czego nauczysz się w tym projekcie?

- Jak stosować **kolekcje i mapy** do rozwiązania realnego problemu.
- Jak **porównać podejście imperatywne** (pętle, computeIfAbsent) z **funkcyjnym** (Stream API).
- Jak budować **algorytm grupowania** oparty na kluczach kanonikach.
- Jak używać **wielu kolekcji razem** (HashMap, List, TreeMap, PriorityQueue).

---

## Opis problemu

**Anagramy** to słowa zbudowane z tych samych liter w różnej kolejności.
Przykłady: `rak`, `kar`, `ark` — wszystkie zawierają litery {a, k, r}.

**Zadanie:** dla podanej listy słów, znajdź wszystkie grupy anagramów.

---

## Diagram — architektura rozwiązania

![Architektura AnagramSolver](diagrams/anagram_design.png)

*Źródło: `diagrams/anagram_design.puml`*

---

## Kluczowa idea: klucz kanoniczny

Każde słowo sprowadzamy do postaci kanonicznej: **posortowane litery**.

```java
static String canonical(String word) {
    char[] chars = word.toLowerCase().toCharArray();
    Arrays.sort(chars);
    return new String(chars);
}

canonical("rak")  // → "akr"
canonical("kar")  // → "akr"
canonical("ark")  // → "akr"
canonical("kat")  // → "akt"
canonical("tak")  // → "akt"
```

Słowa o tym samym kluczu kanonicznym to anagramy. To sprawia, że mogą być pogrupowane przez `HashMap`.

---

## Podejście 1: Imperatywne (pętle + computeIfAbsent)

```java
static Map<String, List<String>> groupAnagramsImperative(List<String> words) {
    Map<String, List<String>> groups = new HashMap<>();

    for (String word : words) {
        String key = canonical(word);
        // computeIfAbsent — jeśli klucza nie ma, utwórz nową listę
        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
    }

    groups.entrySet().removeIf(e -> e.getValue().size() < 2);
    return groups;
}
```

**Złożoność:** O(n × k log k), gdzie n = liczba słów, k = max długość słowa.

Pełny przykład: [`code/AnagramSolver.java`](code/AnagramSolver.java)

---

## Podejście 2: Stream API (Collectors.groupingBy)

```java
static Map<String, List<String>> groupAnagramsStream(List<String> words) {
    return words.stream()
            .collect(Collectors.groupingBy(AnagramSolver::canonical))  // klucz = canonical
            .entrySet().stream()
            .filter(e -> e.getValue().size() >= 2)   // tylko grupy z co najmniej 2 słowami
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
}
```

**Oba podejścia dają ten sam wynik** — wybór zależy od preferencji i czytelności.

---

## Top N grup i wyszukiwanie anagramów konkretnego słowa

```java
// Top N grup wg rozmiaru
groups.entrySet().stream()
    .sorted(Comparator.comparingInt((Map.Entry<String, List<String>> e) ->
        e.getValue().size()).reversed())
    .limit(3)
    .forEach(e -> System.out.println(e.getValue()));

// Anagramy konkretnego słowa
static List<String> findAnagramsOf(String word, List<String> dictionary) {
    String key = canonical(word);
    return dictionary.stream()
            .filter(w -> !w.equalsIgnoreCase(word))
            .filter(w -> canonical(w).equals(key))
            .sorted()
            .collect(Collectors.toList());
}

findAnagramsOf("rak", allWords);  // [ark, kar]
```

---

## Przykładowe wyjście programu

```
=== Demo: grupowanie anagramów ===
Słów wejściowych: 30

--- Podejście imperatywne (pętle + computeIfAbsent) ---
  [akr] → [ark, kar, rak]
  [akt] → [akt, kat, tak]
  [akt] → [akt, kat, tak]
  [akrt] → [karta, ratka, tarka]
  ...

=== Top 3 grup (wg rozmiaru) ===
  [ark, kar, rak] (klucz: akr)
  [akt, kat, tak] (klucz: akt)
  [karta, ratka, tarka] (klucz: aakrt)

=== Anagramy słowa 'rak' ===
[ark, kar]
```

---

## Przegląd użytych kolekcji i technik

| Problem | Użyta technika |
|---------|----------------|
| Grupowanie słów | `HashMap<String, List<String>>` + `computeIfAbsent` |
| Deklaratywne grupowanie | `Collectors.groupingBy` |
| Sortowanie grup | `Comparator.comparingInt().reversed()` |
| Top N | `Stream.limit(n)` |
| Usuwanie grup z 1 słowem | `removeIf` |
| Szukanie anagramów | `Stream.filter()` |

---

## Rozszerzenia dla zaawansowanych

1. **Wczytaj słownik z pliku** — użyj `Files.lines(Path.of("slownik.txt"))` zamiast wbudowanej listy.
2. **Filtr minimalnej długości** — pokaż tylko anagramy słów o długości ≥ 4.
3. **Porównanie wydajności** — zmierz czas dla listy 50 000 słów (imperatywne vs stream).
4. **Palindromy w grupach** — czy w grupie anagramów są palindromy?

---

## Notatki do slajdów (wersja rozszerzona)

### Slajd: Jak rozbic problem anagramow
- Pokaz dekompozycje: normalizacja -> klucz kanoniczny -> grupowanie -> filtracja -> ranking.
- Podkresl, ze kazdy krok mapuje sie na konkretna abstrakcje JCF.

### Slajd: Dlaczego `HashMap<String, List<String>>`
- Klucz kanoniczny to naturalny indeks, lista trzyma elementy tej samej klasy rownowaznosci.
- Wyjasnij, dlaczego to rozwiazanie skaluje sie lepiej niz porownywanie kazdej pary slow.

### Slajd: Imperatywnie vs Stream API
- Porownaj dwie osie: kontrola krok po kroku (imperatywnie) vs zwięzlosc i deklaratywnosc (stream).
- Powiedz studentom, ze wybor stylu to decyzja o czytelnosci zespolowej i profilowaniu, a nie tylko gust.

### Slajd: Zlozonosc i pamiec
- Dominujacy koszt to sortowanie liter kazdego slowa: O(k log k) na slowo.
- Calkowity koszt: O(n * k log k), pamiec O(n * k) na grupy.
- Daj pomysl optymalizacji: zamiast sortowania, histogram liter dla alfabetu stalych rozmiarow.

### Slajd: Jak przejsc do wersji produkcyjnej
- Strumieniowe czytanie slownika z pliku.
- Ograniczenie pamieci (chunking, pipeline).
- Testy wlasciwosci (czy dwa slowa z tym samym kluczem sa anagramami).

### Pytania kontrolne
1. Co jest kluczem kanonicznym i dlaczego dziala?
2. Jak zmienia sie zlozonosc przy bardzo dlugich slowach?
3. Kiedy wersja imperatywna bedzie lepsza od streamowej?

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_05_kolekcje\_09_projekt"
.\run-examples.ps1
```

---

## 📚 Literatura i materiały dodatkowe

- **Effective Java (3rd ed.)**, Joshua Bloch — Item 47: Prefer Collection to Stream as a return type
- **Baeldung — Java groupingBy Collector:** <https://www.baeldung.com/java-groupingby-collector>
- **Algorytm anagramów** opisany w: Sedgewick & Wayne, *Algorithms* (4th ed.), Chapter 5
