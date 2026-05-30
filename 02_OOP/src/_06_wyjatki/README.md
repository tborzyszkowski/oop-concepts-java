# Moduł 06 — Wyjątki w Javie

## Moduły

### Notatka do slajdu: Dlaczego wyjątki istnieją
- Wyjątki rozwiązują problem kodów błędów: pomijanie sprawdzeń, mieszanie logiki i obsługi awarii.
- Pokaz różnicę: "failure as value" vs "failure as control transfer".

### Notatka do slajdu: Trzy poziomy klasyfikacji
- `Error`: awarie środowiska/JVM, zwykle nieobsługiwalne biznesowo.
- Checked `Exception`: sytuacje odzyskiwalne (I/O, sieć, integracje).
- `RuntimeException`: błędy kontraktu i logiki programu.

| Katalog | Temat | Opis |
|---------|-------|------|
| [`_01_wprowadzenie/`](_01_wprowadzenie/README.md) | Wprowadzenie | Geneza, idea, styl C vs Java, Stack trace |
| [`_02_hierarchia_klas/`](_02_hierarchia_klas/README.md) | Hierarchia klas | Throwable, Error, Exception, RuntimeException, multi-catch |
| [`_03_throw_catch/`](_03_throw_catch/README.md) | Mechanizm throw-catch | Łańcuch catch, kolejność, multi-catch, anty-wzorce |
| [`_04_zagniezdzony_try/`](_04_zagniezdzony_try/README.md) | Zagnieżdżony try | Propagacja, refaktoryzacja, 3 poziomy zagnieżdżenia |
| [`_05_throw_rethrow/`](_05_throw_rethrow/README.md) | throw i propagacja | Jawne rzucanie, re-throw, exception chaining |
| [`_06_throws/`](_06_throws/README.md) | Deklaracja throws | Checked vs unchecked, propagacja warstw, interfejsy |
| [`_07_finally/`](_07_finally/README.md) | Sekcja finally | Gwarancja, return w finally, try-with-resources, suppressed |
| [`_08_wlasne_wyjatki/`](_08_wlasne_wyjatki/README.md) | Własne wyjątki | Projektowanie hierarchii, API wyjątku, walidacja zbiorcza |
| [`_09_projekt/`](_09_projekt/README.md) | Projekt: BankApp | Pełna aplikacja bankowa z 4 typami wyjątków domenowych |
| [`_10_zadania/`](_10_zadania/README.md) | Zadania | 7 zadań ⭐–⭐⭐⭐ z rozwiązaniami |

## Wymagania

- Java 21 lub nowsza
- PlantUML — do regeneracji diagramów PNG (opcjonalnie)

## Jak uruchomić przykłady

### Notatka do slajdu: Gdzie łapać wyjątki
- Nisko: gdy można lokalnie naprawić (retry, fallback, domyślna wartość).
- Wysoko: gdy trzeba przetłumaczyć błąd na język warstwy (np. HTTP, UI, komunikat dla użytkownika).
- Unikaj "łapania wszystkiego" bez decyzji o dalszym kroku.

### Sposób 1 — skrypt PowerShell

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\_06_wyjatki
.\run-all-examples.ps1
```

### Sposób 2 — ręcznie

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src

# Kompilacja konkretnego modułu
javac -d out _06_wyjatki/_01_wprowadzenie/code/ExceptionIntroDemo.java

# Uruchomienie
java -cp out _06_wyjatki._01_wprowadzenie.code.ExceptionIntroDemo
```

## Generowanie diagramów PNG

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\_06_wyjatki
.\generate-diagrams.ps1
# lub bezpośrednio:
java -jar ..\..\plantuml.jar "**\diagrams\*.puml"
```

## Struktura plików

### Notatka do slajdu: Jakość diagnostyki
- Komunikat wyjątku ma zawierać kontekst (parametry wejściowe, identyfikatory).
- Zachowuj `cause` przy translacji między warstwami.
- Loguj wyjątek z pełnym stack trace, nie tylko `e.getMessage()`.

```
_06_wyjatki/
├── README.md
├── run-all-examples.ps1
├── generate-diagrams.ps1
│
├── _01_wprowadzenie/
│   ├── README.md
│   ├── code/ExceptionIntroDemo.java
│   └── diagrams/exception_intro.puml/.png
│
├── _02_hierarchia_klas/
│   ├── README.md
│   ├── code/ExceptionHierarchyDemo.java
│   └── diagrams/exception_hierarchy.puml/.png
│
├── _03_throw_catch/
│   ├── README.md
│   ├── code/ThrowCatchDemo.java
│   └── diagrams/throw_catch_flow.puml/.png
│
├── _04_zagniezdzony_try/
│   ├── README.md
│   ├── code/NestedTryDemo.java
│   └── diagrams/nested_try.puml/.png
│
├── _05_throw_rethrow/
│   ├── README.md
│   ├── code/ThrowRethrowDemo.java
│   └── diagrams/throw_rethrow.puml/.png
│
├── _06_throws/
│   ├── README.md
│   ├── code/ThrowsDeclarationDemo.java
│   └── diagrams/throws_declaration.puml/.png
│
├── _07_finally/
│   ├── README.md
│   ├── code/FinallyDemo.java
│   └── diagrams/finally_flow.puml/.png
│
├── _08_wlasne_wyjatki/
│   ├── README.md
│   ├── code/CustomExceptionsDemo.java
│   └── diagrams/custom_exceptions.puml/.png
│
├── _09_projekt/
│   ├── README.md
│   ├── code/BankApp.java
│   └── diagrams/bank_app_architecture.puml/.png
│
└── _10_zadania/
    ├── README.md
    └── solutions/ExceptionExercisesSolutions.java
```

### Notatka do slajdu: Najważniejsze antywzorce
- Pusty `catch`.
- `catch (Exception)` jako domyślny styl kodowania.
- `return` w `finally`.
- Używanie wyjątków do sterowania normalnym przepływem.

### Pytania kontrolne
1. Kiedy checked exception jest lepszy niż unchecked?
2. Co tracimy, jeśli nie przekażemy `cause`?
3. Dlaczego wyjątek nie powinien zastępować warunku `if` w normalnej logice?
