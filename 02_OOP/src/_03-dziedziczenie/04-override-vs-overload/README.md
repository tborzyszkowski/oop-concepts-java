# Moduł 3.4: Nadpisywanie vs przeciążanie metod

## Wprowadzenie

`overload` i `override` to dwa różne mechanizmy, które studenci często mylą. `Overload` dotyczy wielu metod o tej samej nazwie w jednej klasie, a `override` dotyczy redefinicji zachowania odziedziczonego.

### Czego nauczysz się w tym module?
- jak odróżnić `overload` od `override`,
- jak JVM wybiera metodę przy przeciążaniu i nadpisaniu,
- dlaczego `@Override` powinno być standardem.

---

## Diagram koncepcji

![Diagram override/overload](diagrams/override_vs_overload.png)

Diagram PlantUML: [`diagrams/override_vs_overload.puml`](diagrams/override_vs_overload.puml)

---

## Kod i omówienie

Plik z przykładem:
- [`src/inheritance/t04/OverrideVsOverloadDemo.java`](src/inheritance/t04/OverrideVsOverloadDemo.java)

W przykładzie zobaczysz jednocześnie:
- przeciążanie metody po sygnaturze,
- nadpisanie metody z klasy bazowej,
- różnice widoczne na etapie kompilacji i uruchomienia.

---

## Najczęstsze błędy

1. Traktowanie zmiany typu zwracanego jako przeciążania (to nie działa samodzielnie).
2. Literówki w nazwie metody przy `override` bez adnotacji `@Override`.
3. Oczekiwanie dynamicznego wyboru metody dla przeciążania (to decyzja kompilatora).

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
