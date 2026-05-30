# Moduł 05 — Kolekcje w Javie

Moduł omawia **Java Collections Framework (JCF)** — hierarchię interfejsów, najpopularniejsze implementacje,
typy generyczne, iteratory, mapy, komparatory oraz strumienie (`Stream API`).
Styl i struktura są spójne z modułami `_02_interfaces`, `_03_dziedziczenie` i `_04_pakiety`.

## Notatki do slajdów (wersja rozszerzona)

### Slajd 1: Problem i motywacja
- Zacznij od pytania: "Jak przechowac 1 mln rekordow tak, aby wyszukiwanie bylo szybkie i kod czytelny?"
- Pokaz ograniczenia tablic: staly rozmiar, kosztowne wstawianie w srodku, brak semantyki "unikalnosci" i "klucz-wartosc".
- Wprowadz JCF jako zestaw abstrakcji rozwiazujacych te problemy przez interfejsy i wymienne implementacje.

### Slajd 2: Interfejsy, implementacje, algorytmy
- Podkresl, ze JCF sklada sie z 3 warstw: interfejsy (`List`, `Set`, `Queue`, `Map`), implementacje (`ArrayList`, `HashSet`, `TreeMap`), algorytmy (`Collections`, `Collectors`).
- Wyjasnij zasade "programuj do interfejsu" i konsekwencje dla testowalnosci oraz refaktoryzacji.

### Slajd 3: Koszt operacji jako kryterium projektowe
- Wybor kolekcji to decyzja o zlozonosci: O(1), O(log n), O(n).
- Porownaj scenariusze: API online (szybki lookup), raporty (sortowanie), kolejki zadan (priorytety).
- Dodaj uwage, ze stale ukryte i lokalnosc pamieci czesto sa tak samo wazne jak Big-O.

### Slajd 4: Kolekcje a model domeny
- Pokaz, ze struktura danych powinna odzwierciedlac reguly biznesowe:
  - brak duplikatow -> `Set`,
  - historia zdarzen -> `List` lub `Deque`,
  - indeks po kluczu -> `Map`.
- Wspomnij o invariants (np. unikalny identyfikator) i tym, jak kolekcja moze je egzekwowac.

### Slajd 5: Ewolucja stylu - od petli do Stream API
- Pokaz ten sam problem w stylu imperatywnym i strumieniowym.
- Omow kompromisy: czytelnosc, debugowanie, testowalnosc, wydajnosc.
- Zaznacz, ze "nowoczesne" nie znaczy "zawsze lepsze" - decyzja zalezy od kontekstu.

### Slajd 6: Najczestsze bledy architektoniczne
- Niepoprawny kontrakt `equals/hashCode`.
- Naduzywanie `LinkedList` i `TreeMap` bez potrzeby.
- Efekty uboczne w strumieniach i modyfikacja kolekcji podczas iteracji.
- Brak testow scenariuszy brzegowych (pusta kolekcja, `null`, duze dane).

### Slajd 7: Jak zamknac modul
- Zaproponuj checkliste doboru kolekcji (unikalnosc, porzadek, lookup, modyfikacje, skala).
- Polacz modul 05 z kolejnymi tematami: wyjatki (walidacja danych), watki (kolekcje wspolbiezne), wzorce (repozytoria i cache).

---

## Spis treści

| # | Temat | Katalog |
|---|-------|---------|
| 1 | [Pojęcie kolekcji — geneza, hierarchia JCF](_01_kolekcje_intro/README.md) | `_01_kolekcje_intro/` |
| 2 | [Typy kolekcji — kiedy używać której](_02_typy_kolekcji/README.md) | `_02_typy_kolekcji/` |
| 3 | [Typy generyczne — historia i zastosowanie](_03_typy_generyczne/README.md) | `_03_typy_generyczne/` |
| 4 | [HashSet i TreeSet — porównanie](_04_set_hashset_treeset/README.md) | `_04_set_hashset_treeset/` |
| 5 | [Iteratory i pętle foreach](_05_iteratory/README.md) | `_05_iteratory/` |
| 6 | [Mapy — interfejs Map i implementacje](_06_mapy/README.md) | `_06_mapy/` |
| 7 | [Komparatory i Comparable](_07_komparatory/README.md) | `_07_komparatory/` |
| 8 | [Strumienie — Stream API](_08_strumienie/README.md) | `_08_strumienie/` |
| 9 | [Projekt: anagramy i zaawansowane przetwarzanie](_09_projekt/README.md) | `_09_projekt/` |
| 10 | [Zadania do samodzielnego rozwiązania](_10_zadania/README.md) | `_10_zadania/` |

---

## 📅 Sugerowany plan zajęć (2 × 90 min)

### Zajęcia 1 — Kolekcje, typy generyczne, Set, Iterator

1. **Wstęp — co to kolekcja i po co JCF (10 min):** Historia `Vector`/`Hashtable`, JCF w Java 1.2, hierarchia interfejsów. *(moduł 01)*
2. **Typy kolekcji i kiedy je stosować (20 min):** `ArrayList` vs `LinkedList`, `HashSet` vs `TreeSet`, `PriorityQueue`. Przykłady. *(moduł 02)*
3. **Typy generyczne (15 min):** Historia (Java 1.4 — `Object`, Java 5 — generics), type erasure, ograniczenia. *(moduł 03)*
4. **HashSet i TreeSet w praktyce (15 min):** Kontrakt `equals`/`hashCode`, złożoność, kiedy co wybrać. *(moduł 04)*
5. **Iteratory i foreach (10 min):** Wzorzec Iterator, `Iterable`, pętle `for-each`, `ListIterator`. *(moduł 05)*
6. **Pytania / zadania krótkie (20 min)** *(moduł 10)*

### Zajęcia 2 — Mapy, Komparatory, Strumienie, Projekt

1. **Mapy — słownik jako abstrakcja (20 min):** `HashMap`, `TreeMap`, `LinkedHashMap`, iteracja po mapie. *(moduł 06)*
2. **Komparatory i Comparable (15 min):** Dwa podejścia do porządku, łańcuchowanie komparatorów, `Comparator.comparing`. *(moduł 07)*
3. **Stream API (25 min):** Potok: `filter`, `map`, `sorted`, `collect`, `reduce`. `Collectors`. Strumienie równoległe. *(moduł 08)*
4. **Projekt: anagramy (20 min):** Live coding / code review — rozwiązanie z kolekcjami vs strumienie. *(moduł 09)*
5. **Zadania (10 min)** *(moduł 10)*

---

## Uruchamianie przykładów

```powershell
# Z katalogu 02_OOP/src/_05_kolekcje
.\run-all-examples.ps1

# Tylko testy
.\run-tests.ps1

# Generowanie diagramów PNG
.\generate-diagrams.ps1
```

---

## Struktura katalogu

```text
_05_kolekcje/
├── _01_kolekcje_intro/
│   ├── code/CollectionsIntroDemo.java
│   ├── diagrams/collections_hierarchy.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── _02_typy_kolekcji/
│   ├── code/CollectionTypesDemo.java
│   ├── diagrams/collection_types.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── _03_typy_generyczne/
│   ├── code/GenericsDemo.java
│   ├── diagrams/generics.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── _04_set_hashset_treeset/
│   ├── code/SetComparisonDemo.java
│   ├── diagrams/set_hierarchy.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── _05_iteratory/
│   ├── code/IteratorDemo.java
│   ├── diagrams/iterator_pattern.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── _06_mapy/
│   ├── code/MapDemo.java
│   ├── diagrams/map_hierarchy.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── _07_komparatory/
│   ├── code/ComparatorDemo.java
│   ├── diagrams/comparator_comparable.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── _08_strumienie/
│   ├── code/StreamsDemo.java
│   ├── diagrams/streams_pipeline.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── _09_projekt/
│   ├── code/AnagramSolver.java
│   ├── diagrams/anagram_design.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── _10_zadania/
│   ├── tasks/
│   ├── solutions/
│   ├── diagrams/
│   ├── run-examples.ps1
│   └── README.md
├── generate-diagrams.ps1
├── run-all-examples.ps1
├── run-tests.ps1
└── README.md   ← ten plik
```

---

## Cele dydaktyczne

Po ukończeniu modułu student:

- rozumie architekturę Java Collections Framework i hierarchię interfejsów `Collection`, `List`, `Set`, `Queue`, `Map`,
- dobiera właściwy typ kolekcji do rozwiązania problemu (złożoność, porządek, unikalność),
- posługuje się typami generycznymi i rozumie pojęcie type erasure,
- rozróżnia `HashSet` i `TreeSet` oraz zna kontrakt `equals`/`hashCode`,
- stosuje iteratory jawne i niejawne, piszę własne klasy implementujące `Iterable`,
- korzysta z `Map` (`HashMap`, `TreeMap`, `LinkedHashMap`) do budowania słowników,
- definiuje porządek za pomocą `Comparable` i `Comparator`,
- buduje potoki przetwarzania danych ze `Stream API`,
- rozwiązuje złożone problemy przetwarzania danych z eleganckim użyciem kolekcji.

---

## Literatura i źródła

- **Joshua Bloch, *Effective Java* (3rd ed.)** — Items 26–33 (Generics), Items 54–58 (Collections)
- **Oracle Java Tutorial — Collections:** <https://docs.oracle.com/javase/tutorial/collections/index.html>
- **Oracle API — java.util:** <https://docs.oracle.com/en/java/docs/api/java.base/java/util/package-summary.html>
- **Oracle API — java.util.stream:** <https://docs.oracle.com/en/java/docs/api/java.base/java/util/stream/package-summary.html>
- **JEP 355 / JEP 305 — Pattern Matching:** <https://openjdk.org/jeps/305>
- **Baeldung — Java Collections:** <https://www.baeldung.com/java-collections>
- **Baeldung — Java 8 Streams:** <https://www.baeldung.com/java-8-streams>
