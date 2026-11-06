# Moduł 05 — Kolekcje w Javie

Moduł omawia **Java Collections Framework (JCF)** — hierarchię interfejsów, najpopularniejsze implementacje,
typy generyczne, iteratory, mapy, komparatory oraz strumienie (`Stream API`).
Styl i struktura są spójne z modułami `_02_interfaces`, `_03_dziedziczenie` i `_04_pakiety`.

Kolekcja nie jest tylko „pojemnikiem na dane”. Jej wybór opisuje również
reguły domenowe: czy elementy mogą się powtarzać, czy kolejność ma znaczenie,
jak wyszukujemy dane i jak często je modyfikujemy. W tym module najpierw
poznajemy abstrakcje, a dopiero potem dobieramy implementację i sposób
przetwarzania.

---

## Dlaczego JCF?

Tablica jest dobrym wyborem, gdy rozmiar danych jest znany i potrzebny jest
dostęp po indeksie. W aplikacji, w której liczba zamówień zmienia się w czasie,
trzeba jednak samodzielnie zarządzać rozmiarem tablicy, a wstawienie elementu
w środku wymaga przesunięcia pozostałych danych. Tablica nie wyraża też sama
z siebie reguł takich jak unikalność elementów ani wyszukiwanie wartości po
kluczu.

Java Collections Framework (JCF) udostępnia gotowe kontrakty i implementacje
dla tych potrzeb. Zamiast tworzyć własny kontener, można wybrać na przykład
`List` dla uporządkowanych elementów z duplikatami, `Set` dla unikalnych
elementów albo `Map` dla par klucz-wartość.

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

## Jak zbudowany jest JCF?

JCF można rozumieć jako trzy współpracujące warstwy:

1. **Interfejsy** (`List`, `Set`, `Queue`, `Map`) określają znaczenie danych
   i dostępne operacje.
2. **Implementacje** (`ArrayList`, `HashSet`, `TreeMap`) realizują ten
   kontrakt, różniąc się m.in. porządkiem i kosztem operacji.
3. **Algorytmy i operacje** (`Collections`, `Collectors`, Stream API)
   przetwarzają elementy bez konieczności ponownego implementowania typowych
   czynności.

Przykład programowania przez interfejs:

```java
List<String> names = new ArrayList<>();
names.add("Ala");
names.add("Ola");

// Kod korzystający z List nie musi znać szczegółów ArrayList.
names.forEach(System.out::println);
```

Wybór struktury wpływa na koszt: `ArrayList.get(i)` ma koszt O(1), wyszukanie
elementu w nieposortowanej liście O(n), a operacje `TreeSet` zwykle O(log n).
Są to opisy wzrostu kosztu wraz z liczbą elementów, nie gwarancja czasu
wykonania w milisekundach. W praktyce liczą się również stałe, zużycie pamięci
i lokalność danych, dlatego przy nieoczywistych wymaganiach warto mierzyć
rzeczywisty scenariusz.

## Szybka checklista wyboru kolekcji

Przed wybraniem klasy z `java.util` odpowiedz na pytania:

1. Czy przechowujesz pojedyncze elementy, czy pary klucz-wartość?
2. Czy duplikaty są dozwolone?
3. Czy kolejność wstawiania, sortowanie albo priorytet są częścią wymagania?
4. Czy najczęstsza operacja to odczyt po indeksie, wyszukiwanie po kluczu,
   dodawanie na końcu czy usuwanie z początku?
5. Czy dane będą używane współbieżnie i czy potrzebujesz kolekcji
   z `java.util.concurrent`?

Dopiero po odpowiedzi na te pytania porównuj `ArrayList` z `LinkedList` albo
`HashMap` z `TreeMap`. Złożoność Big-O jest ważna, ale nie opisuje całego
kosztu: znaczenie mają także pamięć, lokalność danych, kolejność wyników
i prostota kodu.

## Mapa zależności między tematami

- `List`, `Set` i `Map` określają semantykę przechowywania danych.
- Generyki chronią tę semantykę na etapie kompilacji.
- `equals` i `hashCode` decydują o poprawności `HashSet` i `HashMap`.
- `Comparable` i `Comparator` decydują o porządku w `TreeSet`, `TreeMap`
  i `PriorityQueue`.
- Iterator zapewnia bezpieczne przechodzenie i modyfikowanie kolekcji.
- Stream API opisuje transformacje danych, ale nie zastępuje kolekcji.

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

# Weryfikacja rozwiązań zadań
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

### Kolekcja jako część modelu domenowego

Struktura danych może pomagać utrzymywać reguły aplikacji: `Set` zapobiega
powtórzeniom według przyjętej definicji równości, `List` zachowuje kolejność
zdarzeń, a `Map` pozwala szybko odszukać rekord po kluczu. Nie zastępuje to
jednak walidacji całego modelu — na przykład unikalność identyfikatorów w
bazie danych musi być zagwarantowana również przez bazę.

```text
unikalne elementy       → Set
historia w kolejności   → List
stos / kolejka          → Deque
indeks klucz → rekord   → Map
```

### Ten sam problem: pętla i Stream API

Poniższe warianty wybierają nazwy produktów droższych niż 500 zł. Pętla
imperatywna pokazuje kroki wprost; strumień opisuje przepływ danych. Wybierz
styl, który jest czytelniejszy dla zespołu — strumień nie jest automatycznie
szybszy ani lepszy.

```java
record Product(String name, double price) {}

List<Product> products = List.of(
        new Product("Książka", 80),
        new Product("Monitor", 900));

List<String> names = new ArrayList<>();
for (Product product : products) {
    if (product.price() > 500) names.add(product.name());
}

List<String> namesWithStream = products.stream()
        .filter(product -> product.price() > 500)
        .map(Product::name)
        .toList();
```

### Błędy, których warto unikać

- Nadpisanie `equals` bez zgodnego `hashCode` psuje zachowanie `HashSet`
  i `HashMap`.
- `LinkedList` nie przyspiesza wyszukiwania pozycji; wybieraj ją ze względu
  na operacje na końcach lub interfejs `Deque`.
- Nie modyfikuj zwykłej kolekcji spoza iteratora podczas jej iterowania.
- Testuj przypadki brzegowe: pustą kolekcję, powtórzenia, wartości `null`
  (jeśli są dozwolone) oraz dane o realistycznej wielkości.

Dobór kolekcji zacznij od pytań o unikalność, kolejność i dominujące operacje,
a dopiero potem porównuj implementacje. W kolejnych tematach przydadzą się
także walidacja danych, synchronizacja i kolekcje współbieżne oraz wzorce
cache i indeksowania.

Po ukończeniu modułu student:

- rozumie architekturę Java Collections Framework i hierarchię interfejsów `Collection`, `List`, `Set`, `Queue`, `Map`,
- dobiera właściwy typ kolekcji do rozwiązania problemu (złożoność, porządek, unikalność),
- posługuje się typami generycznymi i rozumie pojęcie type erasure,
- rozróżnia `HashSet` i `TreeSet` oraz zna kontrakt `equals`/`hashCode`,
- stosuje iteratory jawne i niejawne oraz pisze własne klasy implementujące `Iterable`,
- korzysta z `Map` (`HashMap`, `TreeMap`, `LinkedHashMap`) do budowania słowników,
- definiuje porządek za pomocą `Comparable` i `Comparator`,
- buduje potoki przetwarzania danych ze `Stream API`,
- rozwiązuje złożone problemy przetwarzania danych z eleganckim użyciem kolekcji.
- potrafi uzasadnić wybór struktury danych wymaganiami, a nie samą popularnością
  konkretnej klasy.

---

## Literatura i źródła

- **Joshua Bloch, *Effective Java* (3rd ed.)** — Items 26–33 (Generics), Items 54–58 (Collections)
- **Oracle Java Tutorial — Collections:** <https://docs.oracle.com/javase/tutorial/collections/index.html>
- **Oracle API — java.util:** <https://docs.oracle.com/en/java/docs/api/java.base/java/util/package-summary.html>
- **Oracle API — java.util.stream:** <https://docs.oracle.com/en/java/docs/api/java.base/java/util/stream/package-summary.html>
- **Baeldung — Java Collections:** <https://www.baeldung.com/java-collections>
- **Baeldung — Java 8 Streams:** <https://www.baeldung.com/java-8-streams>
