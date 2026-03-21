# _03-dziedziczenie

Modul zawiera komplet materialow do wykladu o dziedziczeniu i polimorfizmie w Java 21+.
Kazdy temat ma oddzielny katalog z:

- `README.md` z teoria, referencjami i fragmentami kodu,
- kodem Java uruchamialnym przez `main` i/lub testy JUnit,
- diagramami PlantUML (`.puml`) oraz wygenerowanymi obrazami (`.png`).

## Mapa tematow

1. [`01-dziedziczenie-intro/README.md`](01-dziedziczenie-intro/README.md) - pojecie dziedziczenia, geneza, znaczenie, implementacja.
2. [`02-zgodnosc-typow/README.md`](02-zgodnosc-typow/README.md) - zgodnosc typow, upcasting/downcasting, `instanceof`.
3. [`03-super/README.md`](03-super/README.md) - `super` w konstruktorach i metodach.
4. [`04-override-vs-overload/README.md`](04-override-vs-overload/README.md) - nadpisywanie metod vs przeciazanie.
5. [`05-wiazanie-dynamiczne/README.md`](05-wiazanie-dynamiczne/README.md) - pozne i wczesne wiazanie, dispatch metod.
6. [`06-polimorfizm-i-wzorce/README.md`](06-polimorfizm-i-wzorce/README.md) - polimorfizm i wzorzec Strategy.
7. [`07-abstrakcyjne-vs-interfejsy/README.md`](07-abstrakcyjne-vs-interfejsy/README.md) - klasy abstrakcyjne a interfejsy.
8. [`08-di-i-abstrakcje/README.md`](08-di-i-abstrakcje/README.md) - definicje abstrakcyjne, DI i luzne powiazania.
9. [`09-final/README.md`](09-final/README.md) - `final` dla klas, metod, pol.
10. [`10-object/README.md`](10-object/README.md) - rola `Object` i kontrakty `equals/hashCode/toString`.
11. [`11-javadoc/README.md`](11-javadoc/README.md) - dokumentowanie API i narzedzie `javadoc`.
12. [`12-zadania/README.md`](12-zadania/README.md) - zadania dla studentow, rozwiazania i testy.

## Cele dydaktyczne

- rozumienie relacji `is-a` i projektowanie hierarchii klas,
- umiejetnosc pracy z polimorfizmem i abstrakcjami,
- swiadome stosowanie `final`, `super`, `Object` oraz Javadoc,
- przygotowanie pod dalsze tematy: SOLID, wzorce projektowe, DI kontenerowe.

## Uruchamianie przykladow

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```

## Generowanie diagramow

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\generate-diagrams.ps1
```

## Testy

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-tests.ps1
```

## Wskazowki pod Pandoc/PPTX

- sekcje README sa podzielone na male, slajdowalne bloki,
- diagramy PNG sa osadzone relatywnie, co ulatwia eksport,
- fragmenty kodu sa krotkie i celowane na pojedyncze koncepcje.

## Literatura

- Joshua Bloch, *Effective Java* (3rd Edition)
- Robert C. Martin, *Clean Architecture*
- Oracle Java Tutorials: https://docs.oracle.com/javase/tutorial/java/IandI/index.html
- Java Language Specification (SE 21): https://docs.oracle.com/javase/specs/

