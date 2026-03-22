# Modul 3.4: Nadpisywanie vs przeciazanie metod

## Wprowadzenie

`overload` i `override` to dwa rozne mechanizmy, ktore studenci czesto myla. `Overload` dotyczy wielu metod o tej samej nazwie w jednej klasie, a `override` dotyczy redefinicji zachowania odziedziczonego.

### Czego nauczysz sie w tym module?
- jak odroznic `overload` od `override`,
- jak JVM wybiera metode przy przeciazaniu i nadpisaniu,
- dlaczego `@Override` powinno byc standardem.

---

## Diagram koncepcji

![Diagram override/overload](diagrams/override_vs_overload.png)

Diagram PlantUML: [`diagrams/override_vs_overload.puml`](diagrams/override_vs_overload.puml)

---

## Kod i omowienie

Plik z przykladem:
- [`src/inheritance/t04/OverrideVsOverloadDemo.java`](src/inheritance/t04/OverrideVsOverloadDemo.java)

W przykladzie zobaczysz jednoczesnie:
- przeciazanie metody po sygnaturze,
- nadpisanie metody z klasy bazowej,
- roznice widoczne na etapie kompilacji i uruchomienia.

---

## Najczestsze bledy

1. Traktowanie zmiany typu zwracanego jako przeciazania (to nie dziala samodzielnie).
2. Literowki w nazwie metody przy `override` bez adnotacji `@Override`.
3. Oczekiwanie dynamicznego wyboru metody dla przeciazania (to decyzja kompilatora).

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
