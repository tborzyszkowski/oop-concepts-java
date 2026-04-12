# Moduł 03 - Dziedziczenie i polimorfizm w Javie

## Spis treści

| # | Temat | Katalog |
|---|-------|---------|
| 1 | [Pojęcie dziedziczenia: geneza i implementacja](_01_dziedziczenie_intro/README.md) | `_01_dziedziczenie_intro/` |
| 2 | [Zgodność typów w hierarchii dziedziczenia](_02_zgodnosc_typow/README.md) | `_02_zgodnosc_typow/` |
| 3 | [Słowo kluczowe `super` w praktyce](_03_super/README.md) | `_03_super/` |
| 4 | [Nadpisywanie vs przeciążanie metod](_04_override_vs_overload/README.md) | `_04_override_vs_overload/` |
| 5 | [Dynamiczne wiązanie metod](_05_wiazanie_dynamiczne/README.md) | `_05_wiazanie_dynamiczne/` |
| 6 | [Polimorfizm i wzorce projektowe](_06_polimorfizm_i_wzorce/README.md) | `_06_polimorfizm_i_wzorce/` |
| 7 | [Klasy abstrakcyjne vs interfejsy](_07_abstrakcyjne_vs_interfejsy/README.md) | `_07_abstrakcyjne_vs_interfejsy/` |
| 8 | [Abstrakcje, DI i wstrzykiwanie zależności](_08_di_i_abstrakcje/README.md) | `_08_di_i_abstrakcje/` |
| 9 | [`final` dla klas, metod i pól](_09_final/README.md) | `_09_final/` |
| 10 | [Klasa `Object` i kontrakty metod bazowych](_10_object/README.md) | `_10_object/` |
| 11 | [Javadoc i dokumentowanie API](_11_javadoc/README.md) | `_11_javadoc/` |
| 12 | [Zadania dla studentów](_12_zadania/README.md) | `_12_zadania/` |

---

## Sugerowany plan zajęć (90 min)

1. **Wstęp (15 min):** relacja `is-a`, dobre i złe przypadki dziedziczenia (`01`, `02`).
2. **Live coding (20 min):** `super`, `override`, `overload` (`03`, `04`).
3. **Model wykonania (15 min):** dynamiczne wiązanie i dispatch metod (`05`).
4. **Projektowanie (20 min):** polimorfizm, wzorce, klasy abstrakcyjne vs interfejsy (`06`, `07`, `08`).
5. **Dyscyplina API (10 min):** `final`, `Object`, kontrakty (`09`, `10`).
6. **Domknięcie (10 min):** Javadoc + zadania i kryteria oceny (`11`, `12`).

---

## Uruchamianie przykładów

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03_dziedziczenie"
.\run-all-examples.ps1
```

## Uruchamianie testów

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03_dziedziczenie"
.\run-tests.ps1
```

## Generowanie diagramów PNG

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03_dziedziczenie"
.\generate-diagrams.ps1
```

---

## Struktura katalogu

```text
_03_dziedziczenie/
|-- _01_dziedziczenie_intro/
|-- _02_zgodnosc_typow/
|-- _03_super/
|-- _04_override_vs_overload/
|-- _05_wiazanie_dynamiczne/
|-- _06_polimorfizm_i_wzorce/
|-- _07_abstrakcyjne_vs_interfejsy/
|-- _08_di_i_abstrakcje/
|-- _09_final/
|-- _10_object/
|-- _11_javadoc/
|-- _12_zadania/
|-- generate-diagrams.ps1
|-- run-all-examples.ps1
|-- run-tests.ps1
`-- README.md
```

Każdy temat zawiera:
- `README.md` z teorią i referencjami do kodu,
- `src/` z kodem uruchamialnym (Java 21+),
- `diagrams/` z plikami `.puml` i wygenerowanymi `.png`.

---

## Literatura i źródła

- Joshua Bloch, *Effective Java* (3rd Edition)
- Robert C. Martin, *Clean Architecture*
- Oracle Tutorials - Inheritance: <https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html>
- Java Language Specification (SE 21): <https://docs.oracle.com/javase/specs/>
