# Modul 3.10: Klasa `Object` i kontrakty metod bazowych

## Wprowadzenie

Kazda klasa w Javie dziedziczy po `Object`. Oznacza to, ze wszystkie obiekty dziela wspolne API, m.in. `equals`, `hashCode`, `toString` oraz `getClass`.

### Czego nauczysz sie w tym module?
- jak poprawnie nadpisywac `equals` i `hashCode`,
- po co definiowac czytelne `toString`,
- jakie sa konsekwencje zlamania kontraktow metod bazowych.

---

## Diagram koncepcji

![Diagram Object](diagrams/object_hierarchy.png)

Diagram PlantUML: [`diagrams/object_hierarchy.puml`](diagrams/object_hierarchy.puml)

---

## Kod i omowienie

Plik z przykladem:
- [`src/inheritance/t10/ObjectClassDemo.java`](src/inheritance/t10/ObjectClassDemo.java)

Przyklad pokazuje zachowanie obiektow w kolekcjach oraz znaczenie spojnosci `equals` i `hashCode`.

---

## Najczestsze bledy

1. Nadpisanie `equals` bez `hashCode`.
2. Uzywanie zmiennych mutowalnych jako podstawy `hashCode`.
3. Brak testow kontraktowych dla rownosci obiektow.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
