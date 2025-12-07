# Zasady SOLID

## Wprowadzenie

**SOLID** to akronim pięciu fundamentalnych zasad projektowania obiektowego, które pomagają tworzyć kod:
- Łatwy w utrzymaniu
- Elastyczny
- Testowalny
- Skalowalny
- Odporny na zmiany

Zasady SOLID zostały spopularyzowane przez Roberta C. Martina ("Uncle Bob") i są podstawą nowoczesnego programowania obiektowego.

## Pięć zasad SOLID

| Zasada | Nazwa | Skrót |
|--------|-------|-------|
| **S** | Single Responsibility Principle | SRP |
| **O** | Open/Closed Principle | OCP |
| **L** | Liskov Substitution Principle | LSP |
| **I** | Interface Segregation Principle | ISP |
| **D** | Dependency Inversion Principle | DIP |

## 1. Single Responsibility Principle (SRP)

### Zasada Pojedynczej Odpowiedzialności

> "A class should have one, and only one, reason to change."

**Kluczowa idea**: Każda klasa powinna mieć tylko jedną odpowiedzialność i tylko jeden powód do zmiany.

**Materiały**: [single_responsibility/README.md](single_responsibility/README.md)

**Przykład**:
```java
// ❌ Naruszenie SRP
class Employee {
    void calculatePay() { }
    void save() { }
    void generateReport() { }
}

// ✅ Zgodność z SRP
class Employee { /* tylko dane */ }
class PayrollCalculator { void calculatePay() { } }
class EmployeeRepository { void save() { } }
class ReportGenerator { void generateReport() { } }
```

## 2. Open/Closed Principle (OCP)

### Zasada Otwarte-Zamknięte

> "Software entities should be open for extension, but closed for modification."

**Kluczowa idea**: Klasy powinny być otwarte na rozszerzanie, ale zamknięte na modyfikację.

**Materiały**: [open_closed/README.md](open_closed/README.md)

**Przykład**:
```java
// ❌ Naruszenie OCP
class AreaCalculator {
    double calculateArea(Object shape) {
        if (shape instanceof Rectangle) { }
        else if (shape instanceof Circle) { }
        // Nowy kształt = modyfikacja
    }
}

// ✅ Zgodność z OCP
interface Shape {
    double calculateArea();
}
class Rectangle implements Shape { }
class Circle implements Shape { }
class Triangle implements Shape { } // Nowy bez modyfikacji!
```

## 3. Liskov Substitution Principle (LSP)

### Zasada Podstawiania Liskov

> "Objects of a superclass should be replaceable with objects of a subclass without breaking the application."

**Kluczowa idea**: Podklasy muszą być podstawialne za swoje klasy bazowe bez zmiany poprawności programu.

**Materiały**: [liskov_substitution/README.md](liskov_substitution/README.md)

**Przykład**:
```java
// ❌ Naruszenie LSP
class Rectangle {
    void setWidth(int w) { width = w; }
    void setHeight(int h) { height = h; }
}
class Square extends Rectangle {
    void setWidth(int w) { 
        width = w; 
        height = w; // ❌ Zmienia kontrakt
    }
}

// ✅ Zgodność z LSP
interface Shape { double getArea(); }
class Rectangle implements Shape { }
class Square implements Shape { }
```

## 4. Interface Segregation Principle (ISP)

### Zasada Segregacji Interfejsów

> "No client should be forced to depend on methods it does not use."

**Kluczowa idea**: Wiele małych, specyficznych interfejsów jest lepszych niż jeden duży, ogólny interfejs.

**Materiały**: [interface_segregation/README.md](interface_segregation/README.md)

**Przykład**:
```java
// ❌ Naruszenie ISP - "Fat Interface"
interface Worker {
    void work();
    void eat();
    void sleep();
}
class Robot implements Worker {
    void eat() { throw new UnsupportedOperationException(); }
}

// ✅ Zgodność z ISP
interface Workable { void work(); }
interface Eatable { void eat(); }
interface Sleepable { void sleep(); }
class Robot implements Workable { }
class Human implements Workable, Eatable, Sleepable { }
```

## 5. Dependency Inversion Principle (DIP)

### Zasada Odwrócenia Zależności

> "Depend upon abstractions, not concretions."

**Kluczowa idea**: 
1. Moduły wysokiego poziomu nie powinny zależeć od modułów niskiego poziomu - oba powinny zależeć od abstrakcji.
2. Abstrakcje nie powinny zależeć od szczegółów - szczegóły powinny zależeć od abstrakcji.

**Materiały**: [dependency_inversion/README.md](dependency_inversion/README.md)

**Przykład**:
```java
// ❌ Naruszenie DIP
class UserService {
    private MySQLDatabase db = new MySQLDatabase(); // Konkretna klasa
}

// ✅ Zgodność z DIP
interface Database { void save(String data); }
class MySQLDatabase implements Database { }
class UserService {
    private Database db; // Abstrakcja
    UserService(Database db) { this.db = db; } // DI
}
```

## Relacje między zasadami SOLID

```
SRP ←→ ISP  (Spójność odpowiedzialności)
 ↓      ↓
OCP ←→ DIP  (Abstrakcja i rozszerzalność)
 ↓
LSP         (Poprawność podstawiania)
```

### Jak zasady współpracują:

- **SRP + OCP**: Klasy z jedną odpowiedzialnością łatwiej rozszerzać
- **OCP + DIP**: Abstrakcje umożliwiają rozszerzanie bez modyfikacji
- **LSP + DIP**: Podstawianie wymaga zależności od abstrakcji
- **ISP + SRP**: Małe interfejsy wspierają pojedyncze odpowiedzialności
- **ISP + DIP**: Segregowane interfejsy ułatwiają odwrócenie zależności

## Korzyści stosowania SOLID

### 1. Utrzymanie kodu
- ✅ Łatwiejsze zrozumienie
- ✅ Mniejsze ryzyko błędów
- ✅ Szybsze wprowadzanie zmian

### 2. Testowalność
- ✅ Łatwe mockowanie
- ✅ Izolowane testy jednostkowe
- ✅ Wyższa pokrycie testami

### 3. Elastyczność
- ✅ Łatwe dodawanie funkcji
- ✅ Wymiana implementacji
- ✅ Adaptacja do zmian

### 4. Reużywalność
- ✅ Komponenty wielokrotnego użytku
- ✅ Niezależne moduły
- ✅ Łatwa integracja

### 5. Skalowalność
- ✅ Struktura wspierająca wzrost
- ✅ Niezależne zespoły
- ✅ Równoległy rozwój

## Struktura materiałów

Każda zasada ma dedykowany katalog zawierający:

```
zasada/
├── README.md              # Wyczerpujące wyjaśnienie
├── diagrams/              # Diagramy PlantUML
│   ├── xxx_violation.puml # Diagram naruszenia
│   ├── xxx_solution.puml  # Diagram rozwiązania
│   └── xxx_class_diagram.puml # Pełny diagram klas
├── before/                # Przykłady naruszenia zasady
│   ├── Class1.java
│   └── Demo.java
├── after/                 # Przykłady zgodne z zasadą
│   ├── Interface.java
│   ├── Implementation.java
│   └── Demo.java
└── tests/                 # Testy jednostkowe (opcjonalnie)
    └── Test.java
```

## Uruchamianie przykładów

### Kompilacja i uruchomienie pojedynczego przykładu:

```powershell
# Kompilacja
javac solid/single_responsibility/before/EmployeeDemo.java

# Uruchomienie
java solid.single_responsibility.before.EmployeeDemo
```

### Kompilacja wszystkich przykładów:

```powershell
# Z katalogu 03_ADVANCED/src/
javac solid/**/*.java
```

## Generowanie diagramów z PlantUML

### Wymagania:
- PlantUML
- Graphviz (dla niektórych typów diagramów)

### Generowanie PNG z pliku .puml:

```powershell
# Pojedynczy diagram
java -jar plantuml.jar solid/single_responsibility/diagrams/srp_violation.puml

# Wszystkie diagramy w katalogu
java -jar plantuml.jar solid/**/diagrams/*.puml
```

## Checklist - Czy mój kod jest SOLID?

### Single Responsibility Principle
- [ ] Każda klasa ma tylko jedną odpowiedzialność?
- [ ] Czy potrafisz opisać klasę jednym zdaniem bez użycia "i" lub "lub"?
- [ ] Czy jest tylko jeden powód do zmiany klasy?

### Open/Closed Principle
- [ ] Czy można dodać nową funkcjonalność bez modyfikacji istniejącego kodu?
- [ ] Czy używasz abstrakcji (interfejsów/klas abstrakcyjnych)?
- [ ] Czy unikasz `instanceof` i `switch` na typach?

### Liskov Substitution Principle
- [ ] Czy podklasy mogą zastąpić klasy bazowe bez problemów?
- [ ] Czy nie wzmacniasz warunków wstępnych w podklasach?
- [ ] Czy nie osłabiasz warunków końcowych?

### Interface Segregation Principle
- [ ] Czy interfejsy są małe i spójne?
- [ ] Czy implementacje nie są zmuszone do pustych metod?
- [ ] Czy klient zależy tylko od metod, których używa?

### Dependency Inversion Principle
- [ ] Czy zależysz od abstrakcji, nie konkretnych klas?
- [ ] Czy używasz Dependency Injection?
- [ ] Czy moduły wysokiego poziomu nie zależą od szczegółów?

## Anty-wzorce (czego unikać)

### ❌ God Object
Klasa, która wie i robi za dużo (naruszenie SRP, ISP)

### ❌ Tight Coupling
Wysokie sprzężenie między klasami (naruszenie DIP, OCP)

### ❌ Fragile Base Class
Zmiana klasy bazowej psuje podklasy (naruszenie LSP)

### ❌ Feature Envy
Klasa nadmiernie korzysta z metod innej klasy (naruszenie SRP)

### ❌ Shotgun Surgery
Jedna zmiana wymaga modyfikacji wielu klas (naruszenie SRP, OCP)

## Praktyczne wskazówki

### 1. Rozpocznij od SRP
To najprostsza zasada do zastosowania i podstawa dla pozostałych.

### 2. Używaj abstrakcji
Interfejsy i klasy abstrakcyjne to klucz do OCP, LSP i DIP.

### 3. Preferuj kompozycję nad dziedziczeniem
Kompozycja często lepiej wspiera SOLID niż dziedziczenie.

### 4. Dependency Injection
Wstrzykiwanie zależności to praktyczne zastosowanie DIP.

### 5. Testuj
Kod zgodny z SOLID jest łatwy do przetestowania.

### 6. YAGNI (You Aren't Gonna Need It)
Nie przesadzaj z abstrakcją - dodawaj ją gdy jest potrzebna.

## Dalsze materiały

### Książki:
- Robert C. Martin, "Clean Code"
- Robert C. Martin, "Agile Software Development: Principles, Patterns, and Practices"
- Martin Fowler, "Refactoring"

### Wzorce projektowe wspierające SOLID:
- **Strategy** (OCP, DIP)
- **Factory** (OCP, DIP)
- **Adapter** (OCP, LSP)
- **Decorator** (OCP, SRP)
- **Observer** (OCP)
- **Template Method** (OCP, LSP)

## Podsumowanie

Zasady SOLID to fundamenty dobrego projektowania obiektowego. Stosowanie ich:

1. **Ułatwia utrzymanie** - kod jest łatwiejszy do zrozumienia i modyfikacji
2. **Redukuje błędy** - zmiany są izolowane i bezpieczniejsze
3. **Zwiększa testowalność** - komponenty są niezależne i łatwe do testowania
4. **Poprawia elastyczność** - system łatwo się adaptuje do zmian
5. **Wspiera współpracę** - jasna struktura ułatwia pracę zespołową

**Pamiętaj**: SOLID to wytyczne, nie dogmaty. Stosuj je rozsądnie, uwzględniając kontekst projektu i jego wymagania.

---

## Licencja

Materiały udostępniane są na licencji [Creative Commons Attribution-NonCommercial 4.0 International License (CC BY-NC 4.0)](https://creativecommons.org/licenses/by-nc/4.0/).

## Autor

Materiały dydaktyczne z programowania obiektowego w Java.

