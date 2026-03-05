# Wprowadzenie do Programowania Obiektowego w Javie

## Moduły

| Katalog | Temat | Opis |
|---------|-------|------|
| [`classes/`](classes/README.md) | Klasy i Obiekty | Klasa, obiekt, pole, metoda, operator `new`, referencje |
| [`fields_and_methods/`](fields_and_methods/README.md) | Pola i Metody | Modyfikatory dostępu, `static`, enkapsulacja |
| [`object_lifecycle/`](object_lifecycle/README.md) | Cykl życia obiektów | Konstruktory, bloki init, Garbage Collector, `try-with-resources` |
| [`control_flow/`](control_flow/README.md) | Instrukcje sterujące | `if/else`, `switch`, pętle — Java vs C vs Python |

## Wymagania

- Java 21 lub nowsza
- (opcjonalnie) PlantUML — do generowania diagramów PNG
- (opcjonalnie) Python 3.10+ — pliki porównawcze `control_flow/comparison/`
- (opcjonalnie) gcc — plik porównawczy `control_flow/comparison/examples.c`

## Jak uruchomić przykłady

### Sposób 1 — Skrypt PowerShell (zalecany)

```powershell
# Wszystkie moduły naraz
cd 02_OOP\src\introduction
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

```bash
# Z katalogu 02_OOP/src
cd 02_OOP/src

# Kompilacja wszystkich plików
javac -d . introduction/classes/basic/*.java
javac -d . introduction/classes/advanced/*.java
javac -d . introduction/fields_and_methods/before/*.java
javac -d . introduction/fields_and_methods/after/*.java
javac -d . introduction/object_lifecycle/basic/*.java
javac -d . introduction/object_lifecycle/advanced/*.java
javac -d . introduction/control_flow/examples/*.java

# Uruchomienie wybranej klasy
java introduction.classes.basic.ClassesDemo
java introduction.control_flow.examples.SwitchPatternDemo
```

## Generowanie diagramów PNG z PlantUML

```bash
# Wszystkie diagramy w jednym module
java -jar plantuml.jar "introduction/classes/diagrams/*.puml"

# Wszystkie diagramy w całym module introduction
java -jar plantuml.jar "introduction/**/diagrams/*.puml"
```

## Struktura plików

```
introduction/
├── README.md                         ← ten plik
├── run-all-examples.ps1              ← uruchomienie wszystkich modułów
│
├── classes/
│   ├── README.md                     ← materiały wykładowe
│   ├── run-classes-examples.ps1
│   ├── basic/
│   │   ├── Dog.java                  ← prosta klasa z polami i metodami
│   │   └── ClassesDemo.java          ← demo tworzenia obiektów
│   ├── advanced/
│   │   ├── BankAccount.java          ← klasa z enkapsulacją
│   │   └── BankAccountDemo.java
│   └── diagrams/
│       ├── class_anatomy.puml
│       ├── object_vs_class.puml
│       └── memory_model.puml
│
├── fields_and_methods/
│   ├── README.md
│   ├── run-fields-examples.ps1
│   ├── before/
│   │   ├── Counter.java              ← anty-wzorzec: publiczne pola
│   │   └── CounterBeforeDemo.java
│   ├── after/
│   │   ├── Counter.java              ← enkapsulacja, static
│   │   ├── MathUtils.java            ← klasa utility (static methods)
│   │   └── FieldsMethodsDemo.java
│   └── diagrams/
│       ├── access_modifiers.puml
│       ├── static_vs_instance.puml
│       └── encapsulation.puml
│
├── object_lifecycle/
│   ├── README.md
│   ├── run-lifecycle-examples.ps1
│   ├── basic/
│   │   ├── Person.java               ← konstruktory, bloki init
│   │   └── PersonDemo.java
│   ├── advanced/
│   │   ├── GcDemo.java               ← Garbage Collector, WeakReference
│   │   └── ResourceHolder.java       ← AutoCloseable, try-with-resources
│   └── diagrams/
│       ├── constructors.puml
│       ├── object_lifecycle.puml
│       └── gc_generations.puml
│
└── control_flow/
    ├── README.md
    ├── run-controlflow-examples.ps1
    ├── examples/
    │   ├── ConditionalsDemo.java     ← if/else, switch klasyczny, switch expression
    │   ├── LoopsDemo.java            ← for, while, do-while, for-each, break
    │   └── SwitchPatternDemo.java    ← Pattern matching Java 21
    ├── comparison/
    │   ├── examples.c                ← C — tylko do wglądu
    │   ├── examples.py               ← Python — tylko do wglądu
    │   └── COMPARISON_TABLE.md       ← tabela porównawcza Java/C/Python
    └── diagrams/
        ├── control_flow_overview.puml
        └── switch_evolution.puml
```

