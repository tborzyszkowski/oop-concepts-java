# 02 - Zgodnosc typow w hierarchii

Typ referencji moze byc ogolniejszy niz typ obiektu (upcasting). Odwrotny kierunek wymaga jawnego rzutowania i walidacji.

![Diagram zgodnosci typow](diagrams/type_compatibility.png)

## Kod

- `src/inheritance/t02/TypeCompatibilityDemo.java`

```java
Vehicle upcasted = new Car();
if (upcasted instanceof Car car) {
    car.openTrunk();
}
```

## Zrodla

- https://docs.oracle.com/javase/specs/jls/se21/html/jls-5.html

