# Instrukcja — Moduł 01: Wprowadzenie do OOP w Javie

## Wymagania

- **Java 21** lub nowsza (`java -version` powinno pokazywać 21+)
- **PlantUML** (opcjonalnie, do generowania diagramów PNG)
  - Pobierz: https://plantuml.com/download
  - Zapisz jako `plantuml.jar` w katalogu `02_OOP/src/`

## Struktura katalogów

```
02_OOP/src/
└── 01-introduction/
    ├── README.md                        ← spis treści modułu
    ├── INSTRUKCJA.md                    ← ten plik
    ├── run-all-examples.ps1             ← uruchamia wszystkie moduły
    │
    ├── classes/                         ← Klasy i Obiekty
    │   ├── README.md
    │   ├── run-classes-examples.ps1
    │   ├── basic/
    │   │   ├── Dog.java
    │   │   └── ClassesDemo.java
    │   ├── advanced/
    │   │   ├── BankAccount.java
    │   │   └── BankAccountDemo.java
    │   └── diagrams/
    │       ├── class_anatomy.puml
    │       ├── object_vs_class.puml
    │       └── memory_model.puml
    │
    ├── fields_and_methods/              ← Pola i Metody
    │   ├── README.md
    │   ├── run-fields-examples.ps1
    │   ├── before/
    │   │   ├── Counter.java             ← anty-wzorzec
    │   │   └── CounterBeforeDemo.java
    │   ├── after/
    │   │   ├── Counter.java             ← enkapsulacja, static
    │   │   ├── MathUtils.java
    │   │   └── FieldsMethodsDemo.java
    │   └── diagrams/
    │       ├── access_modifiers.puml
    │       ├── static_vs_instance.puml
    │       └── encapsulation.puml
    │
    ├── object_lifecycle/                ← Konstruktory i GC
    │   ├── README.md
    │   ├── run-lifecycle-examples.ps1
    │   ├── basic/
    │   │   ├── Person.java
    │   │   └── PersonDemo.java
    │   ├── advanced/
    │   │   ├── GcDemo.java
    │   │   └── ResourceHolder.java
    │   └── diagrams/
    │       ├── constructors.puml
    │       ├── object_lifecycle.puml
    │       └── gc_generations.puml
    │
    └── control_flow/                    ← Instrukcje sterujące
        ├── README.md
        ├── run-controlflow-examples.ps1
        ├── examples/
        │   ├── ConditionalsDemo.java
        │   ├── LoopsDemo.java
        │   └── SwitchPatternDemo.java
        ├── comparison/
        │   ├── examples.c               ← porównanie z C
        │   ├── examples.py              ← porównanie z Pythonem
        │   └── COMPARISON_TABLE.md
        └── diagrams/
            ├── control_flow_overview.puml
            └── switch_evolution.puml
```

---

## Uruchamianie przykładów kodu

### Szybki start — wszystkie moduły naraz

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\01-introduction
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\run-all-examples.ps1
```

### Pojedyncze moduły

Wszystkie skrypty uruchamiamy z katalogu modułu. Skrypt automatycznie ustawia katalog `02_OOP/src` jako korzeń kompilacji.

#### Klasy i Obiekty

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\01-introduction\classes
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\run-classes-examples.ps1
```

**Uruchamiane klasy:**
- `introduction.classes.basic.ClassesDemo` — tworzenie obiektów, pola, metody, referencje
- `introduction.classes.advanced.BankAccountDemo` — enkapsulacja, konstruktor

#### Pola i Metody

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\01-introduction\fields_and_methods
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\run-fields-examples.ps1
```

**Uruchamiane klasy:**
- `introduction.fields_and_methods.before.CounterBeforeDemo` — problemy z public fields
- `introduction.fields_and_methods.after.FieldsMethodsDemo` — enkapsulacja, static, walidacja

#### Konstruktory i GC

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\01-introduction\object_lifecycle
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\run-lifecycle-examples.ps1
```

**Uruchamiane klasy:**
- `introduction.object_lifecycle.basic.PersonDemo` — kolejność inicjalizacji, konstruktory
- `introduction.object_lifecycle.advanced.GcDemo` — garbage collector, WeakReference
- `introduction.object_lifecycle.advanced.ResourceHolder` — try-with-resources

#### Instrukcje sterujące

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\01-introduction\control_flow
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\run-controlflow-examples.ps1
```

**Uruchamiane klasy:**
- `introduction.control_flow.examples.ConditionalsDemo` — if/else, switch, ternary
- `introduction.control_flow.examples.LoopsDemo` — pętle, break, continue, labeled break
- `introduction.control_flow.examples.SwitchPatternDemo` — pattern matching Java 21

### Ręczna kompilacja z linii poleceń

```powershell
# Korzeń kompilacji = 02_OOP/src/01-introduction
# Pakiety (introduction.*) są zadeklarowane w plikach .java
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\01-introduction

# Kompilacja (wynik do katalogu out/)
javac -d out classes/basic/*.java
javac -d out classes/advanced/*.java
javac -d out fields_and_methods/before/*.java
javac -d out fields_and_methods/after/*.java
javac -d out object_lifecycle/basic/*.java
javac -d out object_lifecycle/advanced/*.java
javac --release 21 -d out control_flow/examples/*.java

# Uruchomienie
java -cp out introduction.classes.basic.ClassesDemo
java -cp out introduction.classes.advanced.BankAccountDemo
java -cp out introduction.fields_and_methods.before.CounterBeforeDemo
java -cp out introduction.fields_and_methods.after.FieldsMethodsDemo
java -cp out introduction.object_lifecycle.basic.PersonDemo
java -cp out introduction.object_lifecycle.advanced.GcDemo
java -cp out introduction.object_lifecycle.advanced.ResourceHolder
java -cp out introduction.control_flow.examples.ConditionalsDemo
java -cp out introduction.control_flow.examples.LoopsDemo
java -cp out introduction.control_flow.examples.SwitchPatternDemo
```

---

## Generowanie diagramów PNG (PlantUML)

Pobierz `plantuml.jar` z https://plantuml.com/download i umieść w `02_OOP/src/`.

```powershell
# Z katalogu 02_OOP/src

# Jeden moduł
java -jar plantuml.jar "01-introduction/classes/diagrams/*.puml"
java -jar plantuml.jar "01-introduction/fields_and_methods/diagrams/*.puml"
java -jar plantuml.jar "01-introduction/object_lifecycle/diagrams/*.puml"
java -jar plantuml.jar "01-introduction/control_flow/diagrams/*.puml"

# Wszystkie diagramy naraz
java -jar plantuml.jar "01-introduction/**/diagrams/*.puml"
```

Diagramy PNG pojawią się obok plików `.puml` w katalogach `diagrams/`.

---

## Generowanie slajdów PPTX z Pandoc

Każdy plik `README.md` jest przygotowany do konwersji na slajdy PPTX przez Pandoc.

```powershell
# Instalacja Pandoc: https://pandoc.org/installing.html
# Uruchamiamy z katalogu 02_OOP/src/

# Konwersja pojedynczego modułu
pandoc 01-introduction/classes/README.md -o classes_slides.pptx

# Z własnym szablonem (reference.pptx)
pandoc 01-introduction/classes/README.md `
  --reference-doc=template.pptx `
  -o classes_slides.pptx

# Wszystkie moduły do jednego pliku
pandoc `
  01-introduction/classes/README.md `
  01-introduction/fields_and_methods/README.md `
  01-introduction/object_lifecycle/README.md `
  01-introduction/control_flow/README.md `
  -o oop_introduction_slides.pptx
```

> **Wskazówka:** Nagłówki `##` w plikach README.md stają się tytułami slajdów.
> Nagłówki `###` tworzą podslajdy. Bloki kodu są formatowane jako monospace.

