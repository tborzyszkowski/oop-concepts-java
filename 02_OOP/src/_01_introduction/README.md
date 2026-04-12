# Moduł 01 — Wprowadzenie do Programowania Obiektowego w Javie

## Moduły

| Katalog | Temat | Opis |
|---------|-------|------|
| [`classes/`](classes/README.md) | Klasy i Obiekty | Klasa, obiekt, pole, metoda, operator `new`, referencje |
| [`fields_and_methods/`](fields_and_methods/README.md) | Pola i Metody | Modyfikatory dostępu, `static`, enkapsulacja |
| [`object_lifecycle/`](object_lifecycle/README.md) | Cykl życia obiektów | Konstruktory, kopia płytka/głęboka, Prototype, GC, `try-with-resources` |
| [`control_flow/`](control_flow/README.md) | Instrukcje sterujące | `if/else`, `switch`, pętle — Java vs C vs Python |
| [`tdd/`](tdd/README.md) | TDD — Test-Driven Development | Red-Green-Refactor, JUnit 5, Stack jako przykład |
| [`exercises/`](exercises/README.md) | Zadania dla studentów | Rectangle, TeamCopy, Prototype, Calculator z TDD |

## Wymagania

- Java 21 lub nowsza
- (opcjonalnie) PlantUML — do generowania diagramów PNG
- (opcjonalnie) Python 3.10+ — pliki porównawcze `control_flow/comparison/`
- (opcjonalnie) gcc — plik porównawczy `control_flow/comparison/examples.c`

## Jak uruchomić przykłady

### Sposób 1 — Skrypt PowerShell (zalecany)

```powershell
# Wszystkie moduły naraz
cd 02_OOP\src\01-introduction
.\run-all-examples.ps1

# Lub pojedynczy moduł
cd classes
.\run-classes-examples.ps1

cd ..\fields_and_methods
.\run-fields-examples.ps1

cd ..\object_lifecycle
.\run-lifecycle-examples.ps1

cd ..\control_flow
.\run-controlflow-examples.ps1
```

### Sposób 2 — Ręczna kompilacja z linii poleceń

```powershell
# Korzeń kompilacji = 02_OOP/src/01-introduction
# Pakiety (introduction.*) są zadeklarowane w plikach .java
cd 02_OOP\src\01-introduction

# Kompilacja (wynik do katalogu out/)
javac -d out classes/basic/*.java
javac -d out classes/advanced/*.java
javac -d out fields_and_methods/before/*.java
javac -d out fields_and_methods/after/*.java
javac -d out object_lifecycle/basic/*.java
javac -d out object_lifecycle/advanced/*.java
javac -d out control_flow/examples/*.java

# Uruchomienie wybranej klasy
java -cp out introduction.classes.basic.ClassesDemo
java -cp out introduction.control_flow.examples.SwitchPatternDemo
```

## Generowanie diagramów PNG z PlantUML

```powershell
# Z katalogu 02_OOP/src/
java -jar plantuml.jar "01-introduction/classes/diagrams/*.puml"

# Wszystkie diagramy naraz
java -jar plantuml.jar "01-introduction/**/diagrams/*.puml"
```

## Struktura plików

```
01-introduction/
├── README.md                              ← ten plik
├── run-all-examples.ps1                   ← uruchamia wszystkie moduły
├── run-tests.ps1                          ← uruchamia wszystkie testy JUnit
│
├── classes/
│   ├── README.md                          ← materiały wykładowe: klasy i obiekty
│   ├── run-classes-examples.ps1
│   ├── basic/
│   │   ├── Dog.java                       ← prosta klasa z polami i metodami
│   │   └── ClassesDemo.java               ← demo tworzenia obiektów
│   ├── advanced/
│   │   ├── BankAccount.java               ← klasa z walidacją i enkapsulacją
│   │   └── BankAccountDemo.java
│   └── diagrams/
│       ├── class_anatomy.puml / .png
│       ├── object_vs_class.puml / .png
│       └── memory_model.puml / .png
│
├── fields_and_methods/
│   ├── README.md                          ← materiały wykładowe: pola i metody
│   ├── run-fields-examples.ps1
│   ├── before/
│   │   ├── Counter.java                   ← anty-wzorzec: publiczne pola
│   │   └── CounterBeforeDemo.java
│   ├── after/
│   │   ├── Counter.java                   ← enkapsulacja, pole statyczne
│   │   ├── MathUtils.java                 ← metody statyczne (utility class)
│   │   └── FieldsMethodsDemo.java
│   ├── tests/
│   │   └── CounterAndMathTest.java        ← JUnit 5: 46 testów
│   └── diagrams/
│       ├── access_modifiers.puml / .png
│       ├── static_vs_instance.puml / .png
│       └── encapsulation.puml / .png
│
├── object_lifecycle/
│   ├── README.md                          ← materiały wykładowe: cykl życia
│   ├── run-lifecycle-examples.ps1
│   ├── basic/
│   │   ├── Person.java                    ← konstruktory, bloki init, walidacja
│   │   └── PersonDemo.java
│   ├── advanced/
│   │   ├── GcDemo.java                    ← Garbage Collector, WeakReference
│   │   └── ResourceHolder.java            ← AutoCloseable, try-with-resources
│   ├── copies/
│   │   ├── CopyDemo.java                  ← kopia płytka vs głęboka
│   │   └── PrototypeDemo.java             ← wzorzec Prototype + CharacterRegistry
│   ├── tests/
│   │   ├── PersonTest.java                ← JUnit 5: 13 testów
│   │   └── CopyTest.java                  ← JUnit 5: 8 testów
│   └── diagrams/
│       ├── constructors.puml / .png
│       ├── object_lifecycle.puml / .png
│       ├── gc_generations.puml / .png
│       ├── shallow_vs_deep.puml / .png    ← kopia płytka vs głęboka
│       └── prototype_pattern.puml / .png  ← wzorzec Prototype
│
├── control_flow/
│   ├── README.md                          ← materiały wykładowe: sterowanie
│   ├── run-controlflow-examples.ps1
│   ├── examples/
│   │   ├── ConditionalsDemo.java          ← if/else, switch klasyczny i expression
│   │   ├── LoopsDemo.java                 ← for, while, do-while, for-each, break
│   │   └── SwitchPatternDemo.java         ← Pattern matching Java 21
│   ├── comparison/
│   │   ├── examples.c                     ← C — tylko do wglądu
│   │   ├── examples.py                    ← Python — tylko do wglądu
│   │   └── COMPARISON_TABLE.md            ← tabela Java vs C vs Python
│   └── diagrams/
│       ├── control_flow_overview.puml / .png
│       └── switch_evolution.puml / .png
│
├── tdd/
│   ├── README.md                          ← teoria TDD, JUnit 5, cykl R-G-R
│   ├── src/
│   │   └── Stack.java                     ← generyczny stos — implementacja TDD
│   └── tests/
│       └── StackTest.java                 ← JUnit 5: 19 testów (6 cykli R-G-R)
│
└── exercises/
    ├── README.md                          ← opisy zadań dla studentów
    ├── tasks/
    │   ├── RectangleTask.java             ← zad. 1: enkapsulacja, static (⭐)
    │   ├── TeamCopyTask.java              ← zad. 2: głęboka kopia (⭐⭐)
    │   ├── PrototypeConfigTask.java       ← zad. 3: wzorzec Prototype (⭐⭐⭐)
    │   └── CalcTask.java                  ← zad. 4: TDD — kalkulator (⭐⭐)
    └── solutions/
        └── Solutions.java                 ← wzorcowe rozwiązania zadań 1–4
```
