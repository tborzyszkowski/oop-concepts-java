# Modul 3.12: Zadania do samodzielnego rozwiazania

## Wprowadzenie

Ten temat domyka modul dziedziczenia. Zadania sa nieco trudniejsze od przykladow z poprzednich sekcji, ale mozliwe do wykonania na bazie poznanych koncepcji.

### Czego nauczysz sie w tym module?
- laczenia wielu mechanizmow (dziedziczenie, `super`, `override`, `final`),
- projektowania hierarchii klas pod testy,
- dokumentowania decyzji projektowych.

---

## Diagram przegladowy

![Diagram zadan](diagrams/tasks_overview.png)

Diagram PlantUML: [`diagrams/tasks_overview.puml`](diagrams/tasks_overview.puml)

---

## Zadania

1. **Hierarchia pracownikow (poziom 1):**
   Zaimplementuj `Employee -> Manager -> Director` i pokaz upcasting oraz `override`.
2. **Raporty i abstrakcja (poziom 2):**
   Dodaj klase abstrakcyjna `Report` i co najmniej dwie implementacje.
3. **Niemutowalny kontrakt (poziom 3):**
   Uzyj `final` tak, aby zabezpieczyc API klasy niemutowalnej.

---

## Pliki w tym temacie

- Przyklady uruchomieniowe: [`src/inheritance/t12/TasksDemo.java`](src/inheritance/t12/TasksDemo.java)
- Rozwiazania: [`solutions/InheritanceTaskSolution.java`](solutions/InheritanceTaskSolution.java)
- Testy: [`tests/InheritanceTaskSolutionTest.java`](tests/InheritanceTaskSolutionTest.java)

---

## Kryteria sukcesu

- kompilacja bez ostrzezen krytycznych,
- przejscie testow jednostkowych,
- poprawne uzycie dziedziczenia bez zbednej duplikacji kodu,
- czytelne nazwy klas, metod i komentarze tam, gdzie to potrzebne.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-tests.ps1
```
