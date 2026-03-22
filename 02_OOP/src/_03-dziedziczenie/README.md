# Modul 03 - Dziedziczenie i polimorfizm w Javie

## Spis tresci

| # | Temat | Katalog |
|---|-------|---------|
| 1 | [Pojecie dziedziczenia: geneza i implementacja](01-dziedziczenie-intro/README.md) | `01-dziedziczenie-intro/` |
| 2 | [Zgodnosc typow w hierarchii dziedziczenia](02-zgodnosc-typow/README.md) | `02-zgodnosc-typow/` |
| 3 | [Slowo kluczowe `super` w praktyce](03-super/README.md) | `03-super/` |
| 4 | [Nadpisywanie vs przeciazanie metod](04-override-vs-overload/README.md) | `04-override-vs-overload/` |
| 5 | [Dynamiczne wiazanie metod](05-wiazanie-dynamiczne/README.md) | `05-wiazanie-dynamiczne/` |
| 6 | [Polimorfizm i wzorce projektowe](06-polimorfizm-i-wzorce/README.md) | `06-polimorfizm-i-wzorce/` |
| 7 | [Klasy abstrakcyjne vs interfejsy](07-abstrakcyjne-vs-interfejsy/README.md) | `07-abstrakcyjne-vs-interfejsy/` |
| 8 | [Abstrakcje, DI i wstrzykiwanie zaleznosci](08-di-i-abstrakcje/README.md) | `08-di-i-abstrakcje/` |
| 9 | [`final` dla klas, metod i pol](09-final/README.md) | `09-final/` |
| 10 | [Klasa `Object` i kontrakty metod bazowych](10-object/README.md) | `10-object/` |
| 11 | [Javadoc i dokumentowanie API](11-javadoc/README.md) | `11-javadoc/` |
| 12 | [Zadania dla studentow](12-zadania/README.md) | `12-zadania/` |

---

## Sugerowany plan zajec (90 min)

1. **Wstep (15 min):** relacja `is-a`, dobre i zle przypadki dziedziczenia (`01`, `02`).
2. **Live coding (20 min):** `super`, `override`, `overload` (`03`, `04`).
3. **Model wykonania (15 min):** dynamiczne wiazanie i dispatch metod (`05`).
4. **Projektowanie (20 min):** polimorfizm, wzorce, klasy abstrakcyjne vs interfejsy (`06`, `07`, `08`).
5. **Dyscyplina API (10 min):** `final`, `Object`, kontrakty (`09`, `10`).
6. **Domkniecie (10 min):** Javadoc + zadania i kryteria oceny (`11`, `12`).

---

## Uruchamianie przykladow

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```

## Uruchamianie testow

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-tests.ps1
```

## Generowanie diagramow PNG

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\generate-diagrams.ps1
```

---

## Struktura katalogu

```text
_03-dziedziczenie/
|-- 01-dziedziczenie-intro/
|-- 02-zgodnosc-typow/
|-- 03-super/
|-- 04-override-vs-overload/
|-- 05-wiazanie-dynamiczne/
|-- 06-polimorfizm-i-wzorce/
|-- 07-abstrakcyjne-vs-interfejsy/
|-- 08-di-i-abstrakcje/
|-- 09-final/
|-- 10-object/
|-- 11-javadoc/
|-- 12-zadania/
|-- generate-diagrams.ps1
|-- run-all-examples.ps1
|-- run-tests.ps1
`-- README.md
```

Kazdy temat zawiera:
- `README.md` z teoria i referencjami do kodu,
- `src/` z kodem uruchamialnym (Java 21+),
- `diagrams/` z plikami `.puml` i wygenerowanymi `.png`.

---

## Literatura i zrodla

- Joshua Bloch, *Effective Java* (3rd Edition)
- Robert C. Martin, *Clean Architecture*
- Oracle Tutorials - Inheritance: <https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html>
- Java Language Specification (SE 21): <https://docs.oracle.com/javase/specs/>
