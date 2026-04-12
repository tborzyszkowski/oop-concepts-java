# Moduł 3.2: Zgodność typów w hierarchii dziedziczenia

## Wprowadzenie

W Javie typ referencji może być ogólniejszy niż typ obiektu. To podstawa polimorfizmu i bezpiecznego API. W drugą stronę (downcasting) potrzebna jest ostrożność i najczęściej sprawdzenie `instanceof`.

### Czego nauczysz się w tym module?
- jak działa upcasting i downcasting,
- kiedy rzutowanie jest bezpieczne,
- jak używać `instanceof` z pattern matchingiem.

---

## Diagram koncepcji

![Diagram zgodności typów](diagrams/type_compatibility.png)

Diagram PlantUML: [`diagrams/type_compatibility.puml`](diagrams/type_compatibility.puml)

---

## Kod i omówienie

Plik z przykładem:
- [`code/TypeCompatibilityDemo.java`](code/TypeCompatibilityDemo.java)

Fragment:

```java
Vehicle upcasted = new Car();
if (upcasted instanceof Car car) {
    car.openTrunk();
}
```

To nowoczesny styl Javy (pattern variable `car`) bez ręcznego rzutowania.

---

## Najczęstsze błędy

1. Rzutowanie bez sprawdzenia typu (`ClassCastException`).
2. Zakładanie, że typ referencji decyduje o implementacji metody.
3. Nadużywanie `instanceof` zamiast projektowania lepszego API.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03_dziedziczenie"
.\run-all-examples.ps1
```

---

## Materiały dodatkowe

- JLS, rozdział 5 (Conversions and Promotions): <https://docs.oracle.com/javase/specs/jls/se21/html/jls-5.html>
